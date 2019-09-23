package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    public static final int DEFAULT_PORT = 8080;

    private final int port;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                pool.execute(new RequestHandler(connection));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
