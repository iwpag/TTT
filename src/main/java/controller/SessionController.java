package controller;

import data.Session;
import java.util.Date;
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
        Session session = new Session();
        session.setUserName(userName);
        session.setLoggedOn(new Date());
        repo.addSession(session);
        return session;
    }

    public void removeSession(String userName) {
        repo.removeSession(userName);
    }
    
    
}
