package org.power.requests.struct;

import java.util.List;

/**
 * a list of cookies
 *
 * @author Kuo Hong
 */
public class Cookies extends MultiMap<String, String, Cookie> {
    public Cookies() {
    }

    public Cookies(Cookie... pairs) {
        super(pairs);
    }

    public Cookies(List<Cookie> pairs) {
        super(pairs);
    }
}
