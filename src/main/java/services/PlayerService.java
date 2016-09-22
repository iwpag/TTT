package services;

import data.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * Service that handles reading and updating bank user information
 * @author nilstes
 */
@Path("players")
public class PlayerService extends SecureService  {
    
    private static final Logger log = Logger.getLogger(PlayerService.class.getName());

    @GET
    @Produces("application/json")
    public Collection<Player> getAllPlayers() {
        checkLogon();
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player("knoll"));
        players.add(new Player("tott"));      
	return players;
    }
}
