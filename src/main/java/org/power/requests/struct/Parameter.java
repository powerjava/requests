package org.power.requests.struct;

/**
 * http parameter
 *
 * @author Kuo Hong
 */
public class Parameter extends Pair<String, String> {

    public Parameter(String name, Object value) {
        super(name, String.valueOf(value));
    }

}
