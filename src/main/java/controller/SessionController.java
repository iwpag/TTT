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
        if(!repo.existsSession(userName)){
            Session s=new Session();
            s.setUserName(userName);
            repo.addSession(s);
            return s;
        }
        return null;
    }

    public void removeSession(String userName) {
        if(repo.existsSession(userName)){
            repo.removeSession(userName);
        }
        else System.out.println("Session does not exist with username: " + userName);
    }
    
    public Opponents getPossibleOpponents(String myUserName) {
        Opponents o=new Opponents();
        for (String i : repo.getAllUserNames()){
            if (!i.equals(myUserName)){
                o.addUserName(i);
            }
        }
        
        return o;
        
    }
}