package services;

import controller.SessionController;
import data.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import repo.SessionRepository;

/**
 * Service to handle bank logon and logout using the web-session
 * @author nilstes
 */
@Path("session")
public class SessionService {
    
    private static final Logger log = Logger.getLogger(SessionService.class.getName());
    private SessionController sessionController = new SessionController(new SessionRepository());
    
    @Context
    private HttpServletRequest request;

    @POST
    @Consumes("text/plain")
    @Produces("application/json")
    public Session create(String userName) {
        log.info("Trying to logon");
        Session session = sessionController.createSession(userName);
        request.getSession().invalidate();
        request.getSession().setAttribute("session", session);
        log.log(Level.INFO, "Logged on: session={0}", session);
        return session;
    }
        
    @GET
    @Produces("application/json")
    public Session get(@PathParam("userName") String userName) {
        Session session = (Session)request.getSession().getAttribute("session");
        if(session == null) {
            log.info("Session not found");
            throw new NotFoundException();        
        }
        log.info("Returning session info!");
        return session;
    }
    
    @DELETE
    public void delete() {
        Session session = (Session)request.getSession().getAttribute("session");
        if(session != null) {
            sessionController.removeSession(session.getUserName());
        }
        request.getSession().invalidate();
        log.info("Logged out!");
    }
}
