package controller;

import data.Game;
import data.Player;
import static data.Player.X;
import static data.Player.O;
import static data.Player.e;
import junit.framework.TestCase;

import org.junit.Test;


/**
 * @author nilstes
 */
public class GameAIWinPositionTest extends TestCase {
    
    @Test
    public void testHorizontalWin() {
        Player[][] board = {
            {O,O,O,O,O,e},
            {X,O,e,X,e,e},
            {O,O,e,X,e,e},
            {X,O,e,X,e,e},
            {X,e,X,X,e,e},        
            {X,e,X,X,e,e}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        
        assertNotSame(-1, ai.findHorizontalPattern(0, 0, O,O,O,O,O));
        assertNotSame(-1, ai.findHorizontalPattern(2, 0, O,O,O,O,O));
        assertNotSame(-1, ai.findHorizontalPattern(4, 0, O,O,O,O,O));
        assertEquals(-1, ai.findHorizontalPattern(0, 1, O,O,O,O,O));
        assertEquals(-1, ai.findHorizontalPattern(1, 2, O,O,O,O,O));
    }
    
    @Test
    public void testVerticalWin() {
        Player[][] board = {
            {e,O,O,e,e,e},
            {X,O,e,e,e,e},
            {X,e,X,e,e,e},        
            {X,O,O,e,e,e},
            {X,O,O,e,e,e},
            {X,O,O,e,e,e}
        };
        Game game = new Game("testId", "inviter", "invitee", 3);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        
        assertNotSame(-1, ai.findVerticalPattern(0, 1, X,X,X,X,X));
        assertNotSame(-1, ai.findVerticalPattern(0, 3, X,X,X,X,X));
        assertNotSame(-1, ai.findVerticalPattern(0, 5, X,X,X,X,X));
        assertEquals(-1, ai.findVerticalPattern(1, 1, X,X,X,X,X));
        assertEquals(-1, ai.findVerticalPattern(2, 1, X,X,X,X,X));
    }
    
    @Test
    public void testBiDiagonalWin() {
        Player[][] board = {
            {O,X,O,X,e,X,e,e},
            {X,O,e,X,X,e,e,e},
            {e,e,X,X,e,e,e,e},        
            {X,X,X,e,e,e,e,e},        
            {e,X,e,e,e,e,e,e},        
            {e,e,e,e,e,e,e,e},        
            {e,e,e,e,e,e,e,e},        
            {e,e,e,e,e,e,e,e}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        
        assertNotSame(-1, ai.findBiDiagonalPattern(1 ,4, X,X,X,X,X));
        assertNotSame(-1, ai.findBiDiagonalPattern(3 ,2, X,X,X,X,X));
        assertNotSame(-1, ai.findBiDiagonalPattern(5 ,0, X,X,X,X,X));
        assertEquals(-1, ai.findBiDiagonalPattern(3 ,3, X,X,X,X,X));
        assertEquals(-1, ai.findBiDiagonalPattern(2 ,1, X,X,X,X,X));
        assertEquals(-1, ai.findBiDiagonalPattern(4 ,0, X,X,X,X,X));
    }
    
    @Test
    public void testMainDiagonalWin() {
        Player[][] board = {
            {e,e,O,X,e,e,e,e},
            {X,O,e,X,e,e,e,e},
            {e,e,O,e,e,e,e,e},        
            {X,e,X,O,e,e,e,e},        
            {X,e,X,e,O,e,e,e},        
            {e,e,e,e,e,O,e,e},        
            {X,e,X,e,e,e,e,e},        
            {e,e,X,e,e,e,e,e}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        GameAI ai = new GameAI(game);
        
        assertNotSame(-1, ai.findMainDiagonalPattern(1 ,1, O,O,O,O,O));
        assertNotSame(-1, ai.findMainDiagonalPattern(3, 3, O,O,O,O,O));
        assertNotSame(-1, ai.findMainDiagonalPattern(5 ,5, O,O,O,O,O));
        assertEquals(-1, ai.findMainDiagonalPattern(3 ,2, O,O,O,O,O));
        assertEquals(-1, ai.findMainDiagonalPattern(5 ,4, O,O,O,O,O));
    }
}
