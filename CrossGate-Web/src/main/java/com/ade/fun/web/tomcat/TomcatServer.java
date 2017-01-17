package com.ade.fun.web.tomcat;

import com.ade.fun.web.servlet.AccountServlet;
import com.ade.fun.web.servlet.DefaultServlet;
import com.ade.fun.web.servlet.LoginServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class TomcatServer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void startUp() throws LifecycleException, ServletException {
        logger.info("tomcat server startup...");
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8070);
        File base = new File(System.getProperty("user.dir"));
        Context rootCtx = tomcat.addContext("/cross-gate", base.getAbsolutePath());

        rootCtx.setDocBase(base.getPath());
        addServlet(rootCtx, "/",      new DefaultServlet());
        addServlet(rootCtx, "/acc",   new AccountServlet());
        addServlet(rootCtx, "/login", new LoginServlet());

//        tomcat.getHost().setAppBase();

        rootCtx.setSessionTimeout(30);

        tomcat.start();
        tomcat.getServer().await();
    }

    private void addServlet(Context rootCtx, String page, HttpServlet httpServlet) {
        Tomcat.addServlet(rootCtx, httpServlet.getClass().getName(), httpServlet);
        rootCtx.addServletMapping(page, httpServlet.getClass().getName());
        logger.info("add Mapping: " + page + " -> " + httpServlet.getClass().getName());
    }

    public static void main(String[] args) {
        try {
            new TomcatServer().startUp();
        } catch (LifecycleException | ServletException e) {
            e.printStackTrace();
        }
    }
}
