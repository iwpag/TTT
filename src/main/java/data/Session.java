package data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nilstes
 */
public class Session implements Serializable {
    private String userName;
    private Date loggedOn;

    public Session() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(Date loggedOn) {
        this.loggedOn = loggedOn;
    }

    @Override
    public String toString() {
        return "Session{" + "userName=" + userName + ", loggedOn=" + loggedOn + '}';
    }
}
