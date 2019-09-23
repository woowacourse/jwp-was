import webserver.WebServer;

public class Application {
    public static void main(String[] args) {
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
