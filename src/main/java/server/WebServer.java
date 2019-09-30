package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.concurrency.FixedThreadPool;
import server.concurrency.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final ThreadPool THREAD_POOL = new FixedThreadPool(50);

    private final int port;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            LOGGER.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                THREAD_POOL.execute(new Thread(new RequestHandler(connection)));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
