package user;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.controller.UserCreateController;
import user.controller.UserListController;
import user.controller.UserLoginController;
import webserver.RequestHandler;
import webserver.controller.ControllerMapping;
import webserver.http.request.HttpMethod;
import webserver.http.request.RequestMapping;

public class UserApplication {
    public static final int DEFAULT_PORT = 8080;
    private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 1L, TimeUnit.SECONDS,
                queue);
        initControllerMappings();
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                threadPoolExecutor.execute(new RequestHandler(connection));
            }
        }
    }

    private static void initControllerMappings() {
        ControllerMapping.put(new RequestMapping("/user/create", HttpMethod.POST),
                new UserCreateController());
        ControllerMapping.put(new RequestMapping("/user/login", HttpMethod.POST),
                new UserLoginController());
        ControllerMapping.put(new RequestMapping("/user/list.html", HttpMethod.GET),
                new UserListController());
    }
}
