package org.power.requests;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultRedirectStrategy;

/**
 * Redirect strategy that follow all redirect including post/delete/put/patch
 *
 * @author Kuo Hong
 */
public class AllRedirectStrategy extends DefaultRedirectStrategy {

    /**
     * Redirectable methods.
     */
    private static final String[] REDIRECT_METHODS = new String[]{
            HttpGet.METHOD_NAME,
            HttpPost.METHOD_NAME,
            HttpHead.METHOD_NAME,
            HttpPut.METHOD_NAME,
            HttpPatch.METHOD_NAME,
            HttpDelete.METHOD_NAME
    };

    @Override
    protected boolean isRedirectable(final String method) {
        for (final String m : REDIRECT_METHODS) {
            if (m.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }

}
