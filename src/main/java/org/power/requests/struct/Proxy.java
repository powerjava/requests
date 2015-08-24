package org.power.requests.struct;

/**
 * http proxy / socket proxy
 *
 * @author Kuo Hong
 */
public class Proxy {
    private final Scheme scheme;
    private final String host;
    private final int port;
    private final org.power.requests.struct.AuthInfo authInfo;

    public Proxy(Scheme scheme, String host, int port, AuthInfo authInfo) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.authInfo = authInfo;
    }

    public static Proxy httpProxy(String host, int port, String userName, String password) {
        return new Proxy(Scheme.http, host, port, new org.power.requests.struct.AuthInfo(userName, password));
    }

    public static Proxy httpsProxy(String host, int port, String userName, String password) {
        return new Proxy(Scheme.https, host, port, new AuthInfo(userName, password));
    }

    public static Proxy socketProxy(String host, int port, String userName, String password) {
        return new Proxy(Scheme.socks, host, port, new org.power.requests.struct.AuthInfo(userName, password));
    }

    public static Proxy httpProxy(String host, int port) {
        return new Proxy(Scheme.http, host, port, null);
    }

    public static Proxy httpsProxy(String host, int port) {
        return new Proxy(Scheme.https, host, port, null);
    }

    public static Proxy socketProxy(String host, int port) {
        return new Proxy(Scheme.socks, host, port, null);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public org.power.requests.struct.AuthInfo getAuthInfo() {
        return authInfo;
    }

    public String getUserName() {
        return authInfo.getUserName();
    }

    public String getPassword() {
        return authInfo.getPassword();
    }

    public Scheme getScheme() {
        return scheme;
    }

    public static enum Scheme {
        http, https, socks
    }
}
