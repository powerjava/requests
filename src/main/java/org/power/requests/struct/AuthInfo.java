package org.power.requests.struct;

/**
 * @author Kuo Hong
 */
public class AuthInfo {
    private final String userName;
    private final String password;

    public AuthInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
