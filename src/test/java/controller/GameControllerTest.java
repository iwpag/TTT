package controller;

import data.Game;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;

/**
 * @author nilstes
 */
public class GameControllerTest {

    @Test(expected = NotFoundException.class)
    public void testThatPlayerCannotMoveIfGameDoesNotExist() {
        GameController c = new GameController();
        c.move("player", "nonExistentGameId", 1, 2);
    }

    @Test(expected = ClientErrorException.class)
    public void testThatPlayerCannotMoveIfNotPlayersTurn() {
        GameController c = new GameController();
        Game game = c.createGame("inviter", "invitee", 10);
        if(game.getTurn().equals("inviter")) {
            c.move(game.getGameId(), "invitee", 2, 3);
        } else {
            c.move(game.getGameId(), "inviter", 2, 3);
        }
    }
    
    @Test(expected = ClientErrorException.class)
    public void testThatPlayerCannotMoveToOccupiedSquare() {
        GameController c = new GameController();
        Game game = c.createGame("inviter", "invitee", 10);
        game = c.move(game.getGameId(), game.getTurn(), 2, 2);
        assertNotNull(game);
        c.move(game.getGameId(), game.getTurn(), 2, 2);
    }
    
    @Test(expected = ClientErrorException.class)
    public void testThatPlayerCannotMoveToPositionOutOfRange() {
        GameController c = new GameController();
        Game game = c.createGame("inviter", "invitee", 10);
        assertNotNull(game);
        c.move(game.getGameId(), game.getTurn(), 12, 21);
    }
}
