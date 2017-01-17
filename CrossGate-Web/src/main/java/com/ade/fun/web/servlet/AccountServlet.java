package com.ade.fun.web.servlet;


import com.ade.fun.model.context.ObjectContextFactory;
import com.ade.fun.model.enumeration.Position;
import com.ade.fun.model.persistent.*;
import com.ade.fun.model.persistent.Character;
import com.ade.fun.model.utils.StringUtils;
import com.ade.fun.service.AccountService;
import com.ade.fun.service.impl.AccountServiceImpl;
import com.ade.fun.web.utils.Css;
import com.ade.fun.web.utils.Script;
import org.apache.cayenne.ObjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class AccountServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        try (PrintWriter pw = response.getWriter()) {
            String password = request.getParameter("password");
            if (StringUtils.isPositive(password)) {
                if ("123123".equals(password)) {
                    ObjectContext context = ObjectContextFactory.getInstance().getObjectContext();
                    request.getSession().setAttribute("context", context);
                    pw.write(html(context));
                } else {
                    response.sendRedirect("/cross-gate/login");
                }
            } else {
                Object obj = request.getSession().getAttribute("context");
                if (null != obj && obj instanceof ObjectContext) {
                    ObjectContext context = (ObjectContext) obj;
                    pw.write(html(context));
                } else {
                    response.sendRedirect("/cross-gate/login");
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    private String html(ObjectContext context) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>\n");
        builder.append("  <head>\n");
        builder.append("    <title>login</title>\n");
        builder.append("    <meta charset=\"utf-8\">\n");
        builder.append("    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n");
        builder.append(Css.GRID_TABLE.getValue());
        builder.append("  </head>\n");
        builder.append("  <body>\n");
        builder.append("    <form action=\"/cross-gate/acc\" method=\"post\" >\n");
        builder.append("      <input type=\"text\" name=\"accountCode\"/>\n");
        builder.append("      <button type=\"submit\">search</button>\n");
        builder.append("    </form>\n");
        AccountService accountService = new AccountServiceImpl();
        List list = accountService.getAccount(context, null);
        if (null != list && list.size() > 0) {
            builder.append("<table align=\"center\" class=\"").append(Css.GRID_TABLE.getName()).append("\">");
            builder.append("<tr>");
            builder.append("<th>账号</th>");
            builder.append("<th>类型</th>");
            builder.append("<th>左边人物</th>");
            builder.append("<th>右边人物</th>");
            builder.append("<th>备注</th>");
            for (Object obj : list) {
                builder.append("<tr>");
                Account account = (Account) obj;
                builder.append("<td>").append(account.getAccountCode()).append("</td>");
                builder.append("<td>").append(account.getAccountType().getName()).append("</td>");
                Map<Position, Character> characterMap = account.getCharacter();
                builder.append("<td onclick=\"view(").append(characterMap.get(Position.LEFT).getId()).append(");\">").append(characterMap.get(Position.LEFT).getCharacterName()).append("</td>");
                builder.append("<td onclick=\"view(").append(characterMap.get(Position.RIGHT).getId()).append(");\">").append(characterMap.get(Position.RIGHT).getCharacterName()).append("</td>");
                builder.append("<td>").append(account.getAccountDesc()).append("</td>");
                builder.append("</tr>");
            }
            builder.append("</table>");
        }
        builder.append("  </body>\n");
        builder.append("<dev id=\"dialog\" style=\"display:none;\"></div>");
        builder.append("</html>");
        builder.append(Script.ALERT_);
        return builder.toString();
    }

}

