package controller;

import data.Game;
import data.Player;
import static data.Player.X;
import static data.Player.O;
import static data.Player._;
import junit.framework.TestCase;

import org.junit.Test;


/**
 * @author nilstes
 */
public class GameControllerTest extends TestCase {
    
    @Test
    public void testThatWeCannotMoveIfGameDoesNotExist() {
        GameController c = new GameController();
        assertNull(c.move("player", "nonExistentGameId", 1, 2));
    }
    
    @Test
    public void testHorizontalWin() {
        Player[][] board = {
            {O,O,O},
            {X,O,_},
            {X,_,X}        
        };
        Game game = new Game("testId", "inviter", "invitee", 3);
        game.setBoard(board);
        
        assertTrue(game.isHorizontalWin(0,0));
        assertTrue(game.isHorizontalWin(1,0));
        assertTrue(game.isHorizontalWin(2,0));
        assertFalse(game.isHorizontalWin(0,1));
        assertFalse(game.isHorizontalWin(1,2));
    }
    
    @Test
    public void testVerticalWin() {
        Player[][] board = {
            {X,O,O},
            {X,O,_},
            {X,_,X}        
        };
        Game game = new Game("testId", "inviter", "invitee", 3);
        game.setBoard(board);
        
        assertTrue(game.isVerticalWin(0,0));
        assertTrue(game.isVerticalWin(0,1));
        assertTrue(game.isVerticalWin(0,2));
        assertFalse(game.isVerticalWin(1,1));
        assertFalse(game.isVerticalWin(2,1));
    }
    
    @Test
    public void testBiDiagonalWin() {
        Player[][] board = {
            {O,X,O,X,_},
            {X,O,_,X,_},
            {_,_,X,_,_},        
            {X,X,O,_,_},        
            {_,_,X,_,_}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        
        assertTrue(game.isBiDiagonalWin(2,2));
        assertTrue(game.isBiDiagonalWin(1,3));
        assertTrue(game.isBiDiagonalWin(3,1));
        assertFalse(game.isBiDiagonalWin(3,3));
        assertFalse(game.isBiDiagonalWin(2,1));
        assertFalse(game.isBiDiagonalWin(4,0));
    }
    
    @Test
    public void testMainDiagonalWin() {
        Player[][] board = {
            {O,_,O,X,_},
            {X,O,_,X,_},
            {_,_,O,_,_},        
            {X,_,X,_,_},        
            {_,_,X,_,_}        
        };
        Game game = new Game("testId", "inviter", "invitee", 5);
        game.setBoard(board);
        
        assertTrue(game.isMainDiagonalWin(0,0));
        assertTrue(game.isMainDiagonalWin(1,1));
        assertTrue(game.isMainDiagonalWin(2,2));
        assertFalse(game.isMainDiagonalWin(3,3));
        assertFalse(game.isMainDiagonalWin(5,5));
    }
}
