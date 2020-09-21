package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        ExecutorService es = null;
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            es = Executors.newFixedThreadPool(100);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new DispatcherServlet(connection));
            }
        } finally {
            if (es != null) {
                es.shutdown();
                es.awaitTermination(100, TimeUnit.SECONDS);
            }
            logger.info("thread stop");
        }
    }
}
