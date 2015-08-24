package org.power.requests.struct;

import java.util.List;

/**
 * a list of headers
 *
 * @author Kuo Hong
 */
public class Headers extends org.power.requests.struct.MultiMap<String, String, org.power.requests.struct.Header> {
    public Headers() {
    }

    public Headers(org.power.requests.struct.Header... pairs) {
        super(pairs);
    }

    public Headers(List<org.power.requests.struct.Header> pairs) {
        super(pairs);
    }
}
