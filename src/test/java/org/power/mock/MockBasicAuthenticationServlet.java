package org.power.mock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Liu Dong {@literal <im@dongliu.net>}
 */
public class MockBasicAuthenticationServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        PrintWriter out = res.getWriter();
        String auth = req.getHeader("Authorization");
        if (!allowUser(auth)) {
            // Not allowed, so report he's unauthorized
            res.setHeader("WWW-Authenticate", "BASIC realm=\"test basic auth\"");
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            // Could offer to add him to the allowed user list
        } else {
            // Allowed, so show him the secret stuff
            out.println(req.getRequestURI());
        }
    }

    private boolean allowUser(String auth) throws IOException {

        if (auth == null) {
            return false;
        }
        if (!auth.toUpperCase().startsWith("BASIC ")) {
            return false;
        }
        String encodedToken = auth.substring(6);
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String token = new String(dec.decodeBuffer(encodedToken));

        return "test:password".equals(token);
    }
}
