package com.undebugged.mylyn.tbg.core;
import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * Credentials object for The Bug Genie.
 * <p></p>
 * @author Guido Grazioli
 * @since 0.1.0
 */
public class TBGCredentials {

    public static TBGCredentials create(TaskRepository repository) {
        return new TBGCredentials(repository.getCredentials(AuthenticationType.REPOSITORY));
    }

    private final String username;
    private final String password;

    public TBGCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public TBGCredentials(AuthenticationCredentials credentials) {
        this(credentials.getUserName(), credentials.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "TBGCredentials[username=" + username + ", password=" + password + "]";
    }

}