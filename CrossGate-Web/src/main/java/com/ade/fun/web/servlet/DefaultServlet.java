package com.ade.fun.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * Created by liyang on 17/1/12.
 */
public class DefaultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        try (PrintWriter pw = response.getWriter()) {
            String url = request.getRequestURL().toString();
            String sub = url.substring(url.indexOf("/cross-gate"), url.length());
            String[] subs = sub.split("/");
            if (subs.length > 2) {
                if ("login".equals(subs[2])) {
                    pw.write("<h3>this login page.</h3>\n");
                } else {
                    pw.write("<h3>this other page.</h3>\n");
                }
            }
            System.out.println(sub);
            pw.write("<h3>Default Page.</h3>");
        }
    }

    private void analysisUrl() {

    }

}

