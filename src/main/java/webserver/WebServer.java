package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL_FACTOR = 2;

    public static void main(String args[]) {
        try {
            int port = initializePort(args);
            ExecutorService executorService = initializeThreadPool();
            run(port, executorService);
        } catch (Exception exception) {
            logger.error("Unhandled exception occur. ", exception);
        }
    }

    private static int initializePort(final String[] args) {
        if (hasArgument(args)) {
            return Integer.parseInt(args[0]);
        }
        return DEFAULT_PORT;
    }

    private static ExecutorService initializeThreadPool() {
        int availableProcessorSize = Runtime.getRuntime().availableProcessors();
        int threadPoolSize = availableProcessorSize * THREAD_POOL_FACTOR;
        logger.info("Thread pool size is {}", threadPoolSize);
        return Executors.newFixedThreadPool(threadPoolSize);
    }

    private static void run(final int port, final ExecutorService executorService) throws IOException {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            executeRequest(listenSocket, executorService);
        }
    }

    private static void executeRequest(final ServerSocket listenSocket, final ExecutorService executorService) throws
            IOException {
        Socket connection;
        while (Objects.nonNull(connection = listenSocket.accept())) {
            RequestHandler requestHandler = new RequestHandler(connection);
            executorService.execute(requestHandler);
        }
    }

    private static boolean hasArgument(final String[] args) {
        return Objects.nonNull(args) && args.length != 0;
    }
}
