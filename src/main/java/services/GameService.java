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
import repo.GameRepository;

/**
 * Service that handles reading and updating bank user information
 * @author nilstes
 */
@Path("games")
public class GameService extends SecureService  {
    
    private static final Logger log = Logger.getLogger(GameService.class.getName());
    private static GameController gameController = new GameController(new GameRepository());

    @POST
    @Path("/{numSquares}")
    @Consumes("text/plain")
    @Produces("application/json")
    public Game newGameInvite(String opponentUserName, @PathParam("numSquares") int numSquares) {  // "" opponent means computer AI-player
        log.log(Level.INFO, "GameService.newGameInvite(): {0}, {1}", new Object[]{opponentUserName, numSquares});
        String userName = checkLogon();
        Game game = gameController.createGame(userName, opponentUserName, numSquares);
        log.log(Level.INFO, "Added game invite! game={0}", game);      
        return game;
    }
    
    @POST
    @Path("/{gameId}/moves")
    @Consumes("application/json")
    @Produces("application/json")
    public Game addMove(@PathParam("gameId") String gameId, Move move) {  
        log.log(Level.INFO, "GameService.addMove(): {0}, {1}", new Object[]{gameId, move});
        String userName = checkLogon();
        Game game = gameController.move(gameId, userName, move.getColumn(), move.getRow());
        log.log(Level.INFO, "Added move! game={0}", game);   
        return game;
    }
    
    @POST
    @Path("/{gameId}/accept")
    @Produces("application/json")
    public Game acceptInvite(@PathParam("gameId") String gameId) {  
        String userName = checkLogon(); 
        log.log(Level.INFO, "GameService.acceptInvite(): userName={0}, gameId={1}", new Object[]{userName, gameId});
        Game game = gameController.getGame(gameId);
        game.setInviteAccepted(true);
        return game;
    }
    
    @GET
    @Path("/{gameId}")
    @Produces("application/json")
    public Game get(@PathParam("gameId") String gameId) {
        String userName = checkLogon(); 
        log.log(Level.INFO, "GameService.getGame(): userName={0}, gameId={1}", new Object[]{userName, gameId});
        return gameController.getGame(gameId);
    }
       
    @GET
    @Produces("application/json")
    public List<Game> getGameInvites() {
        String userName = checkLogon();
        List<Game> invites = gameController.getInvites(userName);
        log.log(Level.INFO, "Returning {0} invites to {1}", new Object[]{invites.size(), userName});
        return invites;
    }
}
