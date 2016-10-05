package controller;

import data.Game;
import data.Player;
import static data.Player.O;
import static data.Player.X;
import static data.Player.e;
import data.Position;
import java.util.List;
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
        List<Position> pos = ai.getBestMoves();
        
        assertTrue(pos.contains(Position.at(4, 0)));
    }

    @Test
    public void testThatAICanFindTwoInARow() {
        Player[][] board = {
            {e,e,e,e,e},
            {X,e,e,e,e},
            {e,O,e,e,e},
            {e,e,e,e,e},
            {e,e,e,e,e}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        List<Position> pos = ai.getBestMoves();
        
        assertTrue(pos.contains(Position.at(1, 1)));
        assertTrue(pos.contains(Position.at(2, 2)));
        assertTrue(pos.contains(Position.at(1, 3)));
        assertTrue(pos.contains(Position.at(0, 2)));
        assertTrue(pos.contains(Position.at(2, 1)));
        assertTrue(pos.contains(Position.at(2, 3)));
        assertTrue(pos.contains(Position.at(0, 3)));
        assertEquals(7, pos.size());
    }
    @Test
    public void testThatAICanFindTreeInARow(){
        Player[][] board = {
            {e,e,e,e,e},
            {e,X,X,e,e},
            {e,O,O,e,e},
            {e,e,e,e,e},
            {e,e,e,e,e}        
        };
        
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        List<Position> pos = ai.getBestMoves();
        assertTrue(pos.contains(Position.at(0, 2)));
        assertTrue(pos.contains(Position.at(3, 2)));
        assertEquals(2, pos.size());
    }
    
    @Test
    public void testThatAICanFindFourInARow(){
        Player[][] board = {
            {e,e,e,e,e},
            {e,X,X,O,e},
            {e,O,O,O,e},
            {e,X,X,O,e},
            {e,X,e,e,e}        
        };
        
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        List<Position> pos = ai.getBestMoves();
        assertTrue(pos.contains(Position.at(0, 2)));
        assertTrue(pos.contains(Position.at(4, 2)));
        assertTrue(pos.contains(Position.at(3, 0)));
        assertTrue(pos.contains(Position.at(3, 4)));
        assertEquals(4, pos.size());
    }
    
}
