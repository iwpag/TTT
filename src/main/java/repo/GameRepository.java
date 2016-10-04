package repo;

import data.Game;
import data.Session;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nilstes
 */
public class GameRepository {

    // This should really be a database repo
    private static Map<String, Game> games = new HashMap<String, Game>();
    
    public void removeGame(String gameId) {
        games.remove(gameId);
    }

    public void addGame(Game game) {
        games.put(game.getGameId(), game);
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public Iterable<Game> getAllGames() {
        return games.values();
    }
}
