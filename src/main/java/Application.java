import mvc.config.ControllerScanner;
import server.WebServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        ControllerScanner.scan();

        WebServer webServer;
        if (args == null || args.length == 0) {
            webServer = new WebServer();
        } else {
            int port = Integer.parseInt(args[0]);
            webServer = new WebServer(port);
        }
        webServer.run();
    }
}
