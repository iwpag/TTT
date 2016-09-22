var game;
var lineColor = "#e0e0e0";
var xColor = "#2020ff";
var oColor = "#ffff20";
var numSquares = 10;
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

function handleError(xhr, status, error) {
    if(xhr.status === 401) {
        window.location.href = "loggedout.html";
    } else {
        window.location.href = "error.html?status=" + xhr.status;
    }
}

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

$(document).ready(function() {
                        
    getLoggedOnUser(function(data) {
        var canvas = document.getElementById('board');
        canvas.width = canvasSize;
        canvas.height = canvasSize;
        
        var context = canvas.getContext('2d');
        context.lineWidth = lineWidth;
        context.fillStyle = "#ffffff";

        drawBoardLines(context);

        $("#board").click(function(event) {
            var pos = getBoardPosition(canvas, event);
            
            // todo draw wheel
            
            $.ajax({
                url: 'webresources/games/' + game.gameId + "/moves",
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({column: pos.x, row: pos.y}),
                success: function(data) {        
                    // alert(JSON.stringify(data));
                    game = data;
                    drawBoard(context);
                    if(data.winner != null) {
                        bootbox.alert("Vinneren er " + data.winner + "!");
                    }
                },
                error: function (xhr, status, error) {
                    handleError(xhr, status, error);
                }
            }); 
        });
        
        $("#newRobotInvite").click(function(event) {
            $.ajax({
                url: 'webresources/games',
                type: 'POST',
                dataType: 'json',
                contentType: 'text/plain; charset=utf-8',
                data: "",
                success: function(data) {        
                    game = data;
                    drawBoard(context);
                },
                error: function (xhr, status, error) {
                    handleError(xhr, status, error);
                }
            }); 
        });

        $.ajax({
            url: 'webresources/players',
            type: 'GET',
            dataType: 'json',
            success: function(data) {        
                
             
                //initTable(table, data);
            },
            error: function (xhr, status, error) {
                handleError(xhr, status, error);
            }
        });      
    });
});