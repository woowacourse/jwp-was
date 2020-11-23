package webserver;

import application.controller.ControllerMapper;

public class Application {

    public static void main(String[] args) throws Exception {
        new WebServer().run(new ControllerMapper(), args);
    }
}
