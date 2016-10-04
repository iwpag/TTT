package controller;

import data.Game;
import data.Player;
import static data.Player.O;
import static data.Player.X;
import static data.Player.e;
import data.Position;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author nilstes
 */
public class GameAIBestMoveTest extends TestCase {

    @Test
    public void testThatAICanFindWinPosition() {
        Player[][] board = {
            {O,O,O,O,e},
            {X,O,e,X,e},
            {O,O,e,X,e},
            {X,O,e,X,e},
            {X,X,X,O,e}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        Position pos = ai.getBestMove();
        
        assertEquals(4, pos.x());
        assertEquals(0, pos.y());
    }
}
