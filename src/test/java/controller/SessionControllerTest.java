package controller;

import data.Session;
import junit.framework.TestCase;
import org.junit.Test;
import repo.SessionRepository;
import services.SessionService;

/**
 * @author nilstes
 */
public class SessionControllerTest extends TestCase {

    @Test
    public void testThatYouCanLogInWithOnlyUsername() {
        
        SessionController sc= new SessionController(new SessionRepository());
        Session s= sc.createSession("testName");
        assertTrue(s.getUserName().equals("testName"));
        
    }
    @Test
    public void testThatYouCannotLogInWithSameUsername(){
        SessionController sc= new SessionController(new SessionRepository());
        Session s= sc.createSession("testName");
        
        Session s2= sc.createSession("testName");
        assertFalse(s2!=null);
        
    }
    @Test
    public void testThatOpponentsListDoesntIncludeyourself(){
        SessionController sc= new SessionController(new SessionRepository());
        Session s= sc.createSession("testName4");
        
        Session s2=sc.createSession("testName5");
        for(String i : sc.getPossibleOpponents(s.getUserName()).getUserNames()){
            assertFalse(i.equals(s.getUserName()));
        }
    }
}
