package controller;

import data.Direction;
import data.Game;
import data.Player;
import static data.Player.O;
import static data.Player.X;
import static data.Player.e;
import data.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author nilstes
 */
public class GameAI {
    
    private Game game = null;
    private Random rnd = new Random();
    
    GameAI(Game game) {
        this.game = game;
    }

    /**
     * Find the best move for the robot?
     */
    Position getBestMove() {
        Find find = findPossiblePattern(O,O,O,O,O);
        
        if(find == null) {
            find = findPossiblePattern(O,O,O,O);
        }
        if(find == null) {
            find = findPossiblePattern(O,O,O);
        }
        if(find == null) {
            find = findPossiblePattern(O,O);
        }
        if(find == null) {
            find = findPossiblePattern(O);
        }
        
        return find.getPosition();
    }
    
    /**
     * Tries to find a new pattern AFTER the robot move.
     * Substitutes all free squares with robot O one by one and
     * tries to find the pattern when this square belongs to the robot.
     */
    public Find findPossiblePattern(Player ... pattern) {
        List<Find> foundMoves = new ArrayList<Find>();
        
        // Test all possible moves
        for(int i=0; i<game.getSquares(); i++) {
            for(int j=0; j<game.getSquares(); j++) {
                if(game.getBoard()[j][i] == e) { // If position is empty
                    // Set current square to temporarily to robot player (Robot is always O) 
                    game.getBoard()[j][i] = O;         
                    
                    // Test new pattern
                    Find find = findPattern(i, j, pattern);
                    if(find != null) {
                        foundMoves.add(find);
                    }
                    
                    // Set current back to empty
                    game.getBoard()[j][i] = e; 
                }
            }            
        }
        
        // Choose one of the possible moves (ransom)
        return foundMoves.size()==0?null:foundMoves.get(rnd.nextInt(foundMoves.size()));        
    }
    
    /**
     * Tries to find given pattern on board.
     * The position given by x and y must be part of the pattern
     * @return an object of the type Find containing 
     * - the direction the pattern was in,
     * - the index (position) in the pattern that the x,y position is
     * - the position given by x and y
     */
    public Find findPattern(int x, int y, Player ... pattern) {
        int index = findHorizontalPattern(x, y, pattern);
        if(index  >= 0) {
            return new Find(Direction.Horizontal, index, Position.at(x, y));
        }
        index = findVerticalPattern(x, y, pattern);
        if(index  >= 0) {
            return new Find(Direction.Vertical, index, Position.at(x, y));
        }
        index = findMainDiagonalPattern(x, y, pattern);
        if(index  >= 0) {
            return new Find(Direction.MainDiagonal, index, Position.at(x, y));
        }
        index = findBiDiagonalPattern(x, y, pattern);
        if(index  >= 0) {
            return new Find(Direction.BiDiagonal, index, Position.at(x, y));
        }
        return null;
    }
    
    /**
     * Is the position given by x and y part of a winning 5 in a row?
     */
    public boolean isWin(int x, int y) {
        Player p = game.getBoard()[y][x];
        return isPatternInAnyDirection(x, y, p, p, p, p, p);
    }
    
    public boolean isPatternInAnyDirection(int x, int y, Player ... pattern) {
        return findHorizontalPattern(x, y, pattern) >= 0 || 
                findVerticalPattern(x, y, pattern) >= 0 || 
                findMainDiagonalPattern(x, y, pattern) >= 0 ||
                findBiDiagonalPattern(x, y, pattern) >= 0;
    }
    
    public int findHorizontalPattern(int x, int y, Player ... pattern) {
        for(int i=0; i<pattern.length; i++) { // x-1, y
            List<Position> positions = new ArrayList<Position>();
            for(int j=0; j<pattern.length; j++) { // x+1, y
                positions.add(Position.at(x-i+j, y));
            }
            if(isInRange(positions) && matchPattern(positions, pattern)) {
                return i;
            }
        }
        return -1;
    }
    
    public int findVerticalPattern(int x, int y, Player ... pattern) {
        for(int i=0; i<pattern.length; i++) {
            List<Position> positions = new ArrayList<Position>();
            for(int j=0; j<pattern.length; j++) {
                positions.add(Position.at(x, y-i+j));
            }
            if(isInRange(positions) && matchPattern(positions, pattern)) {
                return i;
            }
        }
        return -1;
    }
    
    public int findMainDiagonalPattern(int x, int y, Player ... pattern) {
        // x+, y+
        for(int i=0; i<pattern.length; i++) {
            List<Position> positions = new ArrayList<Position>();
            for(int j=0; j<pattern.length; j++) {
                positions.add(Position.at(x-i+j, y-i+j));
            }
            if(isInRange(positions) && matchPattern(positions, pattern)) {
                return i;
            }
        }
        return -1;
    }
    
    public int findBiDiagonalPattern(int x, int y, Player ... pattern) {
        // x+, y-
        for(int i=0; i<pattern.length; i++) { 
            List<Position> positions = new ArrayList<Position>();
            for(int j=0; j<pattern.length; j++) {
                positions.add(Position.at(x-i+j, y+i-j));
            }
            if(isInRange(positions) && matchPattern(positions, pattern)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Are all the given positions in the range of the board?
     */
    public boolean isInRange(List<Position> positions) {
        for(Position p : positions) {
            if(p.x() < 0 || p.y() < 0 || p.x() >= game.getBoard().length || p.y() >= game.getBoard().length) {
                return false;
            }
        }  
        return true;
    }
    
    /**
     * Is the given pattern the same as the given positions on the board?
     */
    public boolean matchPattern(List<Position> positions, Player[] pattern) {
        for(int i=0; i<positions.size(); i++) {
            Position p = positions.get(i);
            if(pattern[i] != game.getBoard()[p.y()][p.x()]) {
                return false; // Not equal
            }
        }   
        return true;
    }
}
