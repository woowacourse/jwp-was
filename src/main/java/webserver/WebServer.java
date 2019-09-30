package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int NUMBER_OF_THREAD = 100;
    private static final int TIME_OUT = 100;

    public static void main(String args[]) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

        int port = (args == null || args.length == 0)
                ? DEFAULT_PORT
                : Integer.parseInt(args[0]);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler((connection)));
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(TIME_OUT, TimeUnit.SECONDS);
    }
}
