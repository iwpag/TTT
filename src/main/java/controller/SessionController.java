package controller;

import data.Opponents;
import data.Session;
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
        return null; // todo
    }

    public void removeSession(String userName) {
        // todo
    }
    
    public Opponents getPossibleOpponents(String myUserName) {
        throw null; // todo
    }
}