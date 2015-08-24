package org.power.requests.struct;

/**
 * http header
 *
 * @author Kuo Hong
 */
public class Header extends org.power.requests.struct.Pair<String, String> {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String Accept_Encoding = "Accept-Encoding";
    public static final String Accept_Encoding_COMPRESS = "gzip, deflate";

    public Header(String name, Object value) {
        super(name, String.valueOf(value));
    }

}
