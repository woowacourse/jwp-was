package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.StaticController;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            RequestMapping requestMapping = new RequestMapping(new ServletContainer(), new StaticController());

            Socket connection;
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection, requestMapping));
            }
        }
    }
}
