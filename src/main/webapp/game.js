function displayGameInfo(game) {
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
    var game = data;
    drawBoardMarks(context, game.board);
    displayGameInfo(game);                            
    if(game.winner != null) {
        bootbox.alert((game.winner===""?"Roboten":game.winner) + " vant!");
        game = null;
    }
    return game;
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
    
    ajaxGetLoggedOnUser(function(data) {
        // Page state
        var myUser = data;
        var myGame = null;
        var myInvite = null;

        // Init board
        var boardPixels = 500;
        var boardSquares = 20;
        var canvas = initCanvas(document.getElementById('board'), boardPixels, boardSquares);       
        var context = createContext(canvas);
        drawBoardGrid(context);

        // Create game loop
        setInterval(function() {      
            if(myGame != null) {
                // Check if other player has moved
                if(myGame.turn !== myUser) {
                    ajaxGetGame(myGame.gameId, function(data) {  
                        myGame = updateGame(context, data);
                    });
                }
            } else {
                if(myInvite != null) {
                    // Check if other player has accepted my invite
                    ajaxGetGame(myInvite.gameId, function(data) {  
                        if(data.inviteAccepted) {
                            myInvite = null;
                            myGame = updateGame(context, data);
                        }
                    });
                } else {
                    // Check for invites from other players
                    ajaxGetInvite(function(data) {  
                        var gameId = data.gameId;
                        bootbox.confirm("Aksepter invitasjon fra " + data.inviter + "?", function(result) {
                            if(result === true) {
                                ajaxAcceptGame(gameId, function() {
                                    myGame = updateGame(context, data);
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
            }, boardSquares);
        });

        $("#board").click(function(event) {
            if(myGame != null && myGame.turn === myUser) {
                var pos = getBoardPosition(canvas, event);
                ajaxPostMove(pos, myGame.gameId, function(data) {        
                    myGame = updateGame(context, data);
                });
            }
        });
        
        $("#newRobotInvite").click(function() {
            ajaxPostInvite("", function(data) {        
                myGame = updateGame(context, data);
            }, boardSquares);
        });    
    });
});