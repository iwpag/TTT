package controller;

import data.Game;
import data.Mark;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import services.GameService;

/**
 * @author nilstes
 */
public class GameController {

    private static final Logger log = Logger.getLogger(GameController.class.getName());

    private static Random rnd = new SecureRandom();
    
    static Map<String, Game> games = new HashMap<String, Game>();
    
    public Game createGame(String inviter, String invitee, int squares) {
        String gameId = new BigInteger(128, rnd).toString(36);
        Game game = new Game(gameId, inviter, invitee, squares);
        games.put(gameId, game);
        
        game.setTurn(rnd.nextBoolean() ? inviter : invitee);
        if(game.getTurn().equals("")) { // "" means robot
            makeRobotMove(game);
        }
        return game;
    }
        
    public Game move(String player, String gameId, int x, int y) {
        Game game = games.get(gameId);
        if(game == null) {
            log.warning("Game not found");
            return null;
        }

        // Right turn?
        if(!player.equals(game.getTurn())) {
            log.warning("Wrong turn");
            return null; // kast
        }
        
        // Out of range
        if(x < 0 || y < 0 || x >= game.getSquares() || y >= game.getSquares()) {
            log.warning("Position out of range");
            return null; // kast
        }
        
        // Empty square?
        if(game.getBoard()[y][x] != Mark._) {
            log.warning("Square not empty");
            return null; // kast
        }
        
        // Make move
        game.addMove(x, y, player);
        
        // Robot move?
        if(game.getWinner() == null && game.getNumMoves() < game.getSquares()*game.getSquares()) {
            if(game.getTurn().equals("")) {
                makeRobotMove(game);
            }
        }
        
        return game;
    }

    private void makeRobotMove(Game game) {
        for(int i=0; i<game.getSquares(); i++) {
            for(int j=0; j<game.getSquares(); j++) {
                if(game.getBoard()[j][i] == Mark._) {
                    game.addMove(i, j, "");
                    return;
                }
            }            
        }
    }

    public Game getGame(String gameId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
