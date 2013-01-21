package com.undebugged.mylyn.tbg.core;
import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * Credentials object for The Bug Genie.
 * @author Guido Grazioli
 */
public class TBGCredentials {

    public static TBGCredentials create(TaskRepository repository) {
        return new TBGCredentials(repository.getCredentials(AuthenticationType.REPOSITORY), 
        		repository.getProperty(TBGCorePlugin.PROPERTY_SECURITYKEY));
    }

    private final String username;
    private final String password;
    private final String securityKey;

    public TBGCredentials(String username, String password, String key) {
        this.username = username;
        this.password = password;
        this.securityKey = key;
    }

    public TBGCredentials(AuthenticationCredentials credentials, String key) {
        this(credentials.getUserName(), credentials.getPassword(), key);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    @Override
    public String toString() {
        return "TBGCredentials[username=" + username + ", password=" + password + ", security=" + securityKey + "]";
    }

}