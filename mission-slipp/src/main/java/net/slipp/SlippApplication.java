package net.slipp;

import static net.slipp.ServletDescriptor.*;

import kr.wootecat.dongle.ServletConfig;
import kr.wootecat.dongle.WebServer;

public class SlippApplication {

    public static void main(String[] args) {
        ServletConfig servletConfig = getConfig();
        WebServer webServer = new WebServer(servletConfig);
        webServer.start(args);
    }
}
