package net.slipp;

import static net.slipp.config.ServletDescriptor.*;

import webserver.ServletConfig;
import webserver.WebServer;

public class SlippApplication {
    public static void main(String[] args) {
        ServletConfig servletConfig = new ServletConfig(getConfig());
        WebServer webServer = new WebServer(servletConfig);
        webServer.start(args);
    }
}
