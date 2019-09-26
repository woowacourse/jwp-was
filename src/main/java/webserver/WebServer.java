package webserver;

import http.controller.*;
import http.model.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        int port = getPort(args);

        ControllerHandler controllerHandler = initControllerHandler();

        ExecutorService es = Executors.newFixedThreadPool(100);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection, controllerHandler));
            }
        }
        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
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

    private static ControllerHandler initControllerHandler() {
        ControllerHandler controllerHandler = new ControllerHandler();
        Controller fileResourceController = new FileResourceController();
        Controller signUpController = new SignUpController();

        controllerHandler.addController(new ControllerMapping(HttpMethod.GET, "/*"), fileResourceController);
        controllerHandler.addController(new ControllerMapping(HttpMethod.GET, "/user/create"), signUpController);
        controllerHandler.addController(new ControllerMapping(HttpMethod.POST, "/user/create"), signUpController);
        return controllerHandler;
    }
}
