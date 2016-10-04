package repo;

import data.Session;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nilstes
 */
public class SessionRepository {

    // This should really be a database repo
    private static Map<String, Session> sessions = new HashMap<String, Session>();
    
    public void removeSession(String userName) {
        sessions.remove(userName);
    }

    public void addSession(Session session) {
        sessions.put(session.getUserName(), session);
    }
    
    public boolean existsSession(String userName) {
        return sessions.containsKey(userName);
    }
    
    public Collection<String> getAllUserNames() {
        return sessions.keySet();
    }
}
