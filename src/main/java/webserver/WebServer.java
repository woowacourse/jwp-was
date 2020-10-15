package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_THREAD_SIZE = 100;
    private static final int NO_ARGUMENTS = 0;
    private static final int ARGUMENT_INDEX = 0;

    public static void main(String args[]) throws Exception {
        int port = initPort(args);
        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_SIZE);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            while (true) {
                try {
                    Socket finalConnection = listenSocket.accept();
                    executorService.execute(() -> {
                        RequestHandler requestHandler = new RequestHandler(finalConnection);
                        requestHandler.run();
                    });
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private static int initPort(String[] args) {
        if (args == null || args.length == NO_ARGUMENTS) {
            return DEFAULT_PORT;
        } else {
            return Integer.parseInt(args[ARGUMENT_INDEX]);
        }
    }
}
