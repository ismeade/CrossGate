package com.ade.fun.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        try (PrintWriter pw = response.getWriter()) {
            pw.append("<html>\n");
            pw.append("  <head>\n");
            pw.append("    <title>login</title>\n");
            pw.append("    <meta charset=\"utf-8\">\n");
            pw.append("    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n");
            pw.append("    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css'>\n");
            pw.append("    <style>body {  background: #eee !important;}.form-signin{max-width:380px;padding:15px 35px 45px;margin: 0 auto;}.form-signin .form-control {  position: relative;  font-size: 16px;  height: auto;  padding: 10px;  -webkit-box-sizing: border-box;  -moz-box-sizing: border-box;  box-sizing: border-box;}.form-signin .form-control:focus {  z-index: 2;}.form-signin input[type=\"password\"] {  margin-bottom: 20px;  border-top-left-radius: 0;  border-top-right-radius: 0;}</style>\n");
            pw.append("  </head>\n");
            pw.append("  <body>\n");
            pw.append("<br><br><br><br><br>");
            pw.append("    <form class=\"form-signin\" action=\"/cross-gate/acc\" method=\"post\" >\n");
            pw.append("      <input type=\"password\" class=\"form-control\" name=\"password\" placeholder=\"Password\" required=\"\" autofocus=\"\"/>\n");
            pw.append("      <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Login</button>\n");
            pw.append("    </form>\n");
            pw.append("  </body>\n");
            pw.append("</html>");
        }
    }

}

