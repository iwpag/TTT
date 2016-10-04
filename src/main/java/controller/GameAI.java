package controller;

import data.Direction;
import data.Game;
import data.Player;
import static data.Player.O;
import static data.Player.e;
import data.Position;
import java.util.ArrayList;
import java.util.List;


/**
 * @author nilstes
 */
public class GameAI {
    
    private Game game = null;
    
    GameAI(Game game) {
        this.game = game;
    }

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
 
        return find.getPosition();
    }
    
    public Find findPossiblePattern(Player ... pattern) {
        Find find = null;
        
        // Test all possible moves
        for(int i=0; find == null && i<game.getSquares(); i++) {
            for(int j=0; find == null && j<game.getSquares(); j++) {
                if(game.getBoard()[j][i] == e) { // If position is empty
                    // Set current square to temporarily to robot player (Robot is always O) 
                    game.getBoard()[j][i] = O;         
                    
                    // Test new pattern
                    find = findPattern(i, j, pattern);
                    
                    // Set current back to empty
                    game.getBoard()[j][i] = e; 
                }
            }            
        }
        return find;       
    }
    
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
            if(isInRange(positions) && isRightPlayers(positions, pattern)) {
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
            if(isInRange(positions) && isRightPlayers(positions, pattern)) {
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
            if(isInRange(positions) && isRightPlayers(positions, pattern)) {
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
            if(isInRange(positions) && isRightPlayers(positions, pattern)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isInRange(List<Position> positions) {
        for(Position p : positions) {
            if(p.x() < 0 || p.y() < 0 || p.x() >= game.getBoard().length || p.y() >= game.getBoard().length) {
                return false;
            }
        }  
        return true;
    }
    
    public boolean isRightPlayers(List<Position> positions, Player[] players) {
        for(int i=0; i<positions.size(); i++) {
            Position p = positions.get(i);
            if(players[i] != game.getBoard()[p.y()][p.x()]) {
                return false; // Not equal
            }
        }   
        return true;
    }


}
