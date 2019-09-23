package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import proxy.AccessLoggingAdvice;
import proxy.ElapsedTimeAdvice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        run(args);
    }

    private static void run(String[] args) throws IOException {
        int port = extractPortFromArgs(args);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            keepConnecting(listenSocket);
        }
    }

    private static void keepConnecting(ServerSocket listenSocket) throws IOException {
        int nThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        while (true) {
            final Socket connection = listenSocket.accept();
            logger.debug("get connection");

            if (connection == null) {
                break;
            }

            executor.execute(buildRunConnection(connection));
        }
        logger.debug("finished");
    }

    private static Runnable buildRunConnection(Socket connection) {
        ProxyFactory pf = new ProxyFactory();

        pf.setTarget(new RequestHandler(connection));
        pf.addAdvice(ElapsedTimeAdvice.getInstance());
        pf.addAdvice(AccessLoggingAdvice.getInstance());

        return (Runnable) pf.getProxy();
    }

    private static int extractPortFromArgs(String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        return Integer.parseInt(args[0]);
    }
}
