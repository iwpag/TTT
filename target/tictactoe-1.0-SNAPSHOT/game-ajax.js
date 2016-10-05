function ajaxHandleError(xhr, status, error) {
    myInvite = null;
    if(xhr.status === 401) {
        window.location.href = "loggedout.html";
    } else {
        console.log("xhr.status: " + xhr.status);
        console.log("status: " + status);
        console.log("error: " + error);
        bootbox.alert("Det oppsto en feil!");   // Dette er billig feilhåndtering...
    }
}

function ajaxGetLoggedOnUser(success) {
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
}

function ajaxGetGame(gameId, success) {
    $.ajax({
        url: 'webresources/games/' + gameId,
        type: 'GET',
        dataType: 'json',
        success: success
    });
}

function ajaxGetInvite(success) {
    $.ajax({
        url: 'webresources/games',
        type: 'GET',
        dataType: 'json',
        success: function(data) {  
            if(data.length > 0) {
                success(data[0]);
            }
        }
    });
}

function ajaxAcceptGame(gameId, success) {
    $.ajax({
        url: 'webresources/games/' + gameId + "/accept",
        type: 'POST',
        dataType: 'json',
        success: success,
        error: function (xhr, status, error) {
            ajaxHandleError(xhr, status, error);
        }
    });
}

function ajaxGetPossibleOpponents(success) {
    $.ajax({
        url: 'webresources/opponents',
        type: 'GET',
        dataType: 'json',
        success: success,
        error: function (xhr, status, error) {
            ajaxHandleError(xhr, status, error);
        }
    }); 
}

function ajaxPostInvite(player, success, numSquares) {
    $.ajax({
        url: 'webresources/games/' + numSquares,
        type: 'POST',
        dataType: 'json',
        contentType: 'text/plain; charset=utf-8',
        data: player,
        success: success,
        error: function (xhr, status, error) {
            ajaxHandleError(xhr, status, error);
        }
    });
}

function ajaxPostMove(pos, gameId, success) {
    $.ajax({
        url: 'webresources/games/' + gameId + "/moves",
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({column: pos.x, row: pos.y}),
        success: success,
        error: function (xhr, status, error) {
            ajaxHandleError(xhr, status, error);
        }
    }); 
}
