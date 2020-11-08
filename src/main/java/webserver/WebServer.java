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
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {
        int port = getPort(args);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection));
            }
        }
    }

    private static int getPort(String[] args) {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }
}
