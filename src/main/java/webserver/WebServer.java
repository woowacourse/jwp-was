package webserver;

import http.controller.Controller;
import http.controller.FileResourceController;
import http.controller.HttpRequestHandlers;
import http.controller.UserController;
import http.supoort.RequestMapping;
import http.view.ModelResolver;
import http.view.ViewHandler;
import http.view.ViewResolver;
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

        HttpRequestHandlers httpRequestHandlers = initRequestHandlers();
        ViewHandler viewHandler = initViewHandlers();

        ExecutorService es = Executors.newFixedThreadPool(100);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection, httpRequestHandlers, viewHandler));
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

    private static HttpRequestHandlers initRequestHandlers() {
        HttpRequestHandlers httpRequestHandlers = new HttpRequestHandlers();
        Controller fileHandler = new FileResourceController(RequestMapping.GET("/*"));
        Controller userRequestHandler = new UserController(RequestMapping.GET("/user/create"), RequestMapping.POST("/user/create"));

        httpRequestHandlers.addHandler(fileHandler);
        httpRequestHandlers.addHandler(userRequestHandler);
        return httpRequestHandlers;
    }

    private static ViewHandler initViewHandlers() {
        ViewHandler viewHandler = new ViewHandler();
        viewHandler.addResolver(new ViewResolver());
        viewHandler.addResolver(new ModelResolver());
        return viewHandler;
    }
}
