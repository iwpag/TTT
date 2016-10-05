$(document).ready(function() {
    // Log out
    $.ajax({
        url: 'webresources/session/',
        type: 'DELETE'
    });

    // Log in
    $("#loginForm").submit(function(event) {
        event.preventDefault();
        $.ajax({
            url: 'webresources/session',
            type: 'POST',
            data: $("#inputUserName").val(),
            contentType: 'text/plain; charset=utf-8',
            dataType: 'json',
            complete: function(xhr) {
                switch (xhr.status) {
                    case 200:
                        window.location.href="game.html";
                        break;
                    case 401:
                        $("#notLoggedOnAlert").removeClass("hide");
                        break;
                    default:
                        window.location.href = "loggedout.html";
                        break;
                }
            }
        });
    });
});