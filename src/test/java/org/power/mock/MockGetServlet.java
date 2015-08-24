package org.power.mock;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Liu Dong {@literal <im@dongliu.net>}
 */
public class MockGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String queryStr = request.getQueryString();

        // cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                response.addCookie(cookie);
            }
        }

        PrintWriter out = response.getWriter();

        switch (uri) {
            case "/redirect":
                response.sendRedirect("/");
                break;
            default:
                out.println(uri);
                out.println(queryStr);
        }
    }
}
