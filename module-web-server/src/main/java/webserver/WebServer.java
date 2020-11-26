package webserver;

import controller.Controller;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ControllerMapper;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int NUMBER_OF_AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();
    private static final int BLOCKING_COEFFICIENT = 0;

    public WebServer(Map<String, Controller> controllers) {
        ControllerMapper.addControllers(controllers);
    }

    public void run(Integer userPort) throws Exception {
        int port = initPort(userPort);
        ExecutorService executorService
            = Executors.newFixedThreadPool(NUMBER_OF_AVAILABLE_CORES * (1 + BLOCKING_COEFFICIENT));

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            while (true) {
                try {
                    Socket connection = listenSocket.accept();
                    executorService.execute(new RequestHandler(connection));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private int initPort(Integer port) {
        if (Objects.isNull(port)) {
            return DEFAULT_PORT;
        }

        return port.intValue();
    }
}
