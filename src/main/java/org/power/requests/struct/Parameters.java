package org.power.requests.struct;

import java.util.List;

/**
 * a list of parameters
 *
 * @author Kuo Hong
 */
public class Parameters extends org.power.requests.struct.MultiMap<String, String, org.power.requests.struct.Parameter> {
    public Parameters() {
    }

    public Parameters(org.power.requests.struct.Parameter... pairs) {
        super(pairs);
    }

    public Parameters(List<org.power.requests.struct.Parameter> pairs) {
        super(pairs);
    }
}
