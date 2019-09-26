package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.controller.Controller;
import servlet.controller.ControllerFinder;
import servlet.controller.CreateUserController;
import servlet.controller.LoginUserController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL_COUNT = 100;
    private static Map<String, Controller> api;

    static {
        api = new HashMap<>();
        api.put("/user/create", new CreateUserController());
        api.put("/user/login", new LoginUserController());
    }

    public static void main(String args[]) throws Exception {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_COUNT);
        ControllerFinder controllerFinder = new ControllerFinder(Collections.unmodifiableMap(api));

        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection, controllerFinder));
                logger.info("remain Thread Count : {}", THREAD_POOL_COUNT - executorService.getActiveCount());
            }
        }

        executorService.shutdown();
    }
}
