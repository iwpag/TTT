package controller;

import data.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.NotAuthorizedException;
import repo.SessionRepository;

/**
 * @author nilstes
 */
public class SessionController {
    
    private SessionRepository repo;

    public SessionController(SessionRepository repo) {
        this.repo = repo;
    }
    
    public Session createSession(String userName) {
        if(repo.getAllUserNames().contains(userName)) {
            throw new NotAuthorizedException("Bruker " + userName + " er allerede pålogget");
        }  
        Session session = new Session();
        session.setUserName(userName);
        session.setLoggedOn(new Date());
        repo.addSession(session);
        return session;
    }

    public void removeSession(String userName) {
        repo.removeSession(userName);
    }
    
    public List<String> getAllLoggedOnPlayers() {
        return new ArrayList<String>(repo.getAllUserNames());
    }
}