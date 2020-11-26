import application.controller.ControllerMapper;
import webserver.WebServer;

public class Application {

    public static void main(String[] args) throws Exception {
        new WebServer().run(new ControllerMapper(), args);
    }
}
