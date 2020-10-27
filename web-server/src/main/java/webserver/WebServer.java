package webserver;

import http.AbstractServlet;
import http.ControllerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[], List<AbstractServlet> controllers) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        initControllerMapper(controllers);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            ExecutorService es = Executors.newFixedThreadPool(6);
            while ((connection = listenSocket.accept()) != null) {
                ControllerMapper controllerMapper = ControllerMapper.getInstance();
                es.execute(new RequestHandler(connection, controllerMapper));
            }
        }
    }

    private static void initControllerMapper(List<AbstractServlet> controllers) {
        ControllerMapper controllerMapper = ControllerMapper.getInstance();
        for (AbstractServlet controller : controllers) {
            controllerMapper.addController(controller);
        }
    }
}
