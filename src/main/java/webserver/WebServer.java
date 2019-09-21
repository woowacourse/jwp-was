package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    private final int port;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                thread.start();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
