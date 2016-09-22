package services;

import controller.GameController;
import data.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

/**
 * Service that handles reading and updating bank user information
 * @author nilstes
 */
@Path("games")
public class GameService extends SecureService  {
    
    private static final Logger log = Logger.getLogger(GameService.class.getName());

    private static GameController gameController = new GameController();

    @POST
    @Consumes("text/plain")
    @Produces("application/json")
    public Game newGameInvite(String opponentUserName) {  // "" opponent means computer AI-player
        log.info("GameService.newGameInvite(): " + opponentUserName);
        String userName = checkLogon();
        try {
            Game game = gameController.createGame(userName, opponentUserName, 10);
            log.info("Added game invite!");        
            return game;
        } catch(Exception e) {
            log.log(Level.SEVERE, "Failed to add game invite", e);        
            throw new ServerErrorException("Failed to add game invite", Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }
    
    @POST
    @Path("/{gameId}/moves")
    @Consumes("application/json")
    @Produces("application/json")
    public Game addMove(@PathParam("gameId") String gameId, Move move) {  
        log.info("GameService.addMove(): " + gameId + ", " + move);
        String userName = checkLogon();
        try {
            Game game = gameController.move(userName, gameId, move.getColumn(), move.getRow());
            log.info("Added move! game=" + game);   
            return game;
        } catch(Exception e) {
            log.log(Level.SEVERE, "Failed to add move", e);        
            throw new ServerErrorException("Failed to add move", Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }
    
    @GET
    @Path("/{gameId}")
    @Produces("application/json")
    public Game get(@PathParam("gameId") String gameId) {
        checkLogon(); 
        return gameController.getGame(gameId);
    }
       
    @GET
    @Produces("application/json")
    public List<Game> getGameInvites() {
        checkLogon();
	return null; // todo
    }
}
