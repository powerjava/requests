package org.easy.request.util;

import java.util.ResourceBundle;

/**
 * RequestsUtils.
 *
 * @author Kuo Hong on 2015/8/23 0023.
 */
public class RequestsUtils {
    private static ResourceBundle rb;
    private static String conf = "conf/requests";

    static {
        rb = ResourceBundle.getBundle(conf);
    }

    public static String getString(String key) {
        if (rb.containsKey(key)) {
            return rb.getString(key);
        } else {
            return null;
        }
    }

    public static String getString(String key, String defaultValue) {
        if (rb.containsKey(key)) {
            return rb.getString(key);
        } else {
            return defaultValue;
        }
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        if (rb.containsKey(key)) {
            return NumberUtils.toInt(rb.getString(key), defaultValue);
        } else {
            return defaultValue;
        }
    }

    public static Integer getInteger(String key) {
        if (rb.containsKey(key)) {
            return NumberUtils.createInteger(rb.getString(key));
        } else {
            return null;
        }
    }

    public static int MAX_TOTAL_CONNECTIONS() {
        return getInteger("requests.MAX_TOTAL_CONNECTIONS", 100);
    }

    public static int MAX_ROUTE_CONNECTIONS() {
        return getInteger("requests.MAX_ROUTE_CONNECTIONS", 10);
    }

    public static int WAIT_TIMEOUT() {
        return getInteger("requests.WAIT_TIMEOUT", 3000);
    }

    public static int SOCKET_TIME_OUT() {
        return getInteger("requests.SOCKET_TIME_OUT", 3000);
    }

    public static int CONNECTION_TIME_OUT() {
        return getInteger("requests.CONNECTION_TIME_OUT", 6000);
    }

    public static int CONNECTION_REQUEST_TIME_OUT() {
        return getInteger("requests.CONNECTION_REQUEST_TIME_OUT", 3000);
    }

    public static String DEFAULT_USERAGENT() {
        return getString("requests.default.userAgent", "");
    }
}
