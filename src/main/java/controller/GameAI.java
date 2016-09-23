package controller;

import data.Game;
import data.Player;

/**
 * @author nilstes
 */
public class GameAI {

    public void move(Game game) {
        // This AI sucks.
        // Improve it!
        for(int i=0; i<game.getSquares(); i++) {
            for(int j=0; j<game.getSquares(); j++) {
                if(game.getBoard()[j][i] == Player._) {
                    game.addMove(i, j, "");
                    return;
                }
            }            
        }
    }
}
