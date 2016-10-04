package controller;

import data.Game;
import data.Player;
import data.Position;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


/**
 * @author nilstes
 */
public class GameController {

    private static final Logger log = Logger.getLogger(GameController.class.getName());
    private static Random rnd = new SecureRandom();  
    private static Map<String, Game> games = new HashMap<String, Game>();
    
    public Game createGame(String inviter, String invitee, int squares) {
        String gameId = new BigInteger(128, rnd).toString(36);
        Game game = new Game(gameId, inviter, invitee, squares);
        game.setTurn(rnd.nextBoolean() ? inviter : invitee);
        games.put(gameId, game);
            
        // Robot game?
        if(game.getTurn().equals("")) {
            game.setInviteAccepted(true);
            game.addMove(squares/2, squares/2, ""); // Middle of board
        }
        return game;
    }
        
    public Game move(String gameId, String player, int x, int y) {
        Game game = getGame(gameId);
        
        // Right turn?
        if(!player.equals(game.getTurn())) {
            log.warning("Wrong turn");
            throw new ClientErrorException("Ikke denne spillerens tur", Response.Status.BAD_REQUEST);
        }
        
        // Out of range?
        if(x < 0 || y < 0 || x >= game.getSquares() || y >= game.getSquares()) {
            log.log(Level.WARNING, "Position out of range: {0},{1}", new Object[]{x, y});
            throw new ClientErrorException("Posisjon er ugyldig: " + x + "," + y, Response.Status.BAD_REQUEST);
        }
        
        // Empty square?
        if(game.getBoard()[y][x] != Player.e) {
            log.log(Level.WARNING, "Square not empty: {0},{1}", new Object[]{x, y});
            throw new ClientErrorException("Rute ikke tom: " + x + "," + y, Response.Status.BAD_REQUEST);
        }
        
        GameAI ai =  new GameAI(game);
        
        // Make move
        game.addMove(x, y, player);
        if(ai.isWin(x, y)) {
            game.setWinner(player);
        }
    
        // Robot move?
        if(game.getWinner() == null && game.getNumMoves() < game.getSquares()*game.getSquares() && game.getTurn().equals("")) {
            Position pos = ai.getBestMove();
            if(pos == null) {
                throw new ServerErrorException("Couldn't find move", Status.INTERNAL_SERVER_ERROR);
            }
            game.addMove(pos.x(), pos.y(), "");
            if(ai.isWin(pos.x(), pos.y())) {
                game.setWinner("");
            }
        }
        
        return game;
    }

    public Game getGame(String gameId) {
        Game game = games.get(gameId);
        if(game == null) {
            log.log(Level.WARNING, "Game not found: {0}", gameId);
            throw new NotFoundException("Spill med id " + gameId + " ble ikke funnet");
        }
        return game;
    }

    public List<Game> getInvites(String userName) {
        List<Game> invites = new ArrayList<Game>();
        for(Game game : games.values()) {
            if(userName.equals(game.getInvitee()) && !game.isInviteAccepted() && !game.isInviteCommunicated()) {
                game.setInviteCommunicated(true);
                invites.add(game);
            }
        }
        return invites;
    }
}
