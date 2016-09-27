// Global state
var game = null;
var myInvite = null;
var myUser = null;

// Constants
var lineColor = "#e0e0e0";
var xColor = "#2020ff";
var oColor = "#ffff20";
var numSquares = 16;
var lineWidth = 4;
var canvasSize = 500;
var squareSize = canvasSize / numSquares;

function drawO(context, pos) {
    var centerX = (pos.x+0.5) * squareSize;
    var centerY = (pos.y+0.5) * squareSize;
    var radius = squareSize / 2 - lineWidth;
    context.strokeStyle = oColor;
    context.beginPath();
    context.arc(centerX, centerY, radius, 0, 2 * Math.PI);
    context.stroke();
}

function drawX(context, pos) {
    var left = pos.x * squareSize + lineWidth*2;
    var top = pos.y * squareSize + lineWidth*2;
    var right = (pos.x+1) * squareSize - lineWidth*2;
    var bottom = (pos.y+1) * squareSize - lineWidth*2;
    context.strokeStyle = xColor;
    context.beginPath();
    context.moveTo(left, top);
    context.lineTo(right, bottom);
    context.moveTo(left, bottom);
    context.lineTo(right, top);
    context.stroke();
}

function clearPosition(context, pos) {
    context.fillRect(
        pos.x*squareSize + lineWidth/2, 
        pos.y*squareSize + lineWidth/2, 
        squareSize-lineWidth, 
        squareSize-lineWidth
    );
}

function drawBoardLines(context) {
    context.lineCap = 'round';
    context.strokeStyle = lineColor;
    context.beginPath();

    for (var i = 1;i < numSquares;i++) {  
        context.moveTo(lineWidth, i * squareSize);
        context.lineTo(canvasSize - lineWidth, i * squareSize);
        context.moveTo(i * squareSize, lineWidth);
        context.lineTo(i * squareSize, canvasSize - lineWidth);
    }

    context.stroke();
}

function getBoardPosition (canvas, event) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: Math.trunc((event.clientX - rect.left) / squareSize),
        y: Math.trunc((event.clientY - rect.top) / squareSize)
    }
}
     
function getLoggedOnUser(success) {
    $.ajax({
        url: 'webresources/session',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            myUser = data.userName;
            success(data.userName);
        },
        error: function (xhr, status, error) {
            if(xhr.status === 404) { // No session
                window.location.href = "loggedout.html";
            } else {
                handleError(xhr, status, error);
            }
        }                   
    });
};

function drawBoard(context) {
    for(x=0; x<numSquares; x++) {
        for(y=0; y<numSquares; y++) {
            clearPosition(context, {x : x, y : y});
            var mark = game.board[y].item[x];
            if(mark === "X") {
                drawX(context, {x : x, y : y});
            } else if(mark === "O") {
                drawO(context, {x : x, y : y});
            }
        }
    }
}

function displayGameInfo() {
    if(game != null) {
        $("#xPlayer").html(game.inviter);
        $("#oPlayer").html(game.invitee === "" ? "robot" : game.invitee);
        $("#turn").html(game.winner == null?game.turn:"");
    } else {
        $("#xPlayer").html("");
        $("#oPlayer").html("");
        $("#turn").html("");
    }
}

function updateGame(context, data) {
    game = data;
    drawBoard(context);
    displayGameInfo();                            
    if(data.winner != null) {
        bootbox.alert((data.winner===""?"Roboten":data.winner) + " vant!");
        game = null;
    }
}

function showNewInviteModal(userNames) {
    if(userNames.length === 0) {
        bootbox.alert("Det er ingen andre pålogget akkurat nå");
    } else {
        $('#player').html(""); // Clear
        $.each(userNames, function(index, value) {   
            $('#player').append($("<option></option>").attr("value", value).text(value)); 
        });

        // Set modal visible
        $('#newPlayerInviteModal').modal('show');
    }
}
                
$(document).ready(function() {
                        
    getLoggedOnUser(function(data) {
        
        // Init board
        var canvas = document.getElementById('board');
        canvas.width = canvasSize;
        canvas.height = canvasSize;
        var context = canvas.getContext('2d');
        context.lineWidth = lineWidth;
        context.fillStyle = "#ffffff";
        drawBoardLines(context);

        // Create game loop
        setInterval(function() {      
            if(game != null) {
                // Check if other player has moved
                if(game.turn !== myUser) {
                    ajaxGetGame(game.gameId, function(data) {  
                        updateGame(context, data);
                    });
                }
            } else {
                if(myInvite != null) {
                    // Check if other player has accepted my invite
                    ajaxGetGame(myInvite.gameId, function(data) {  
                        if(data.inviteAccepted) {
                            myInvite = null;
                            updateGame(context, data);
                        }
                    });
                } else {
                    // Check for invites from other players
                    ajaxGetInvite(function(data) {  
                        var gameId = data.gameId;
                        bootbox.confirm("Aksepter invitasjon fra " + data.inviter + "?", function(result) {
                            if(result === true) {
                                ajaxAcceptGame(gameId, function() {
                                    updateGame(context, data);
                                });
                            }
                        });
                    });
                }
            }
        }, 3000);

        // Create event handlers
        $("#newPlayerInvite").click(function() {
            ajaxGetPossibleOpponents(function(data) {        
                showNewInviteModal(data.userNames);
            });
        });

        $("#newInviteSubmit").click(function() {
            $('#newPlayerInviteModal').modal('hide');
            ajaxPostInvite($("#player").val(), function(data) {   
                myInvite = data;
            });
        });

        $("#board").click(function(event) {
            if(game != null && game.turn === myUser) {
                var pos = getBoardPosition(canvas, event);
                ajaxPostMove(pos, game.gameId, function(data) {        
                    updateGame(context, data);
                });
            }
        });
        
        $("#newRobotInvite").click(function() {
            ajaxPostInvite("", function(data) {        
                updateGame(context, data);
            });
        });    
    });
});