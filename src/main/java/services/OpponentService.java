package services;

import controller.SessionController;
import data.Opponents;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import repo.SessionRepository;


/**
 * Service that handles reading and updating bank user information
 * @author nilstes
 */
@Path("opponents")
public class OpponentService extends SecureService  {

    private static final Logger log = Logger.getLogger(OpponentService.class.getName());

    private SessionController sessionController = new SessionController(new SessionRepository());

    @GET
    @Produces("application/json")
    public Opponents getAllPlayers() {
        String userName = checkLogon();
        List<String> allPlayers = sessionController.getAllLoggedOnPlayers();
        allPlayers.remove(userName);
        Opponents opponents = new Opponents();
        opponents.setUserNames(allPlayers);
        log.log(Level.INFO, "Returning {0} possible opponents", allPlayers.size());
	return opponents;
    }
}
