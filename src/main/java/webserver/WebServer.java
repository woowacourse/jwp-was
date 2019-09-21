package webserver;

import http.controller.Controller;
import http.controller.FileResourceController;
import http.controller.HttpRequestHandlers;
import http.controller.UserController;
import http.session.HttpSessionManager;
import http.session.RandomGenerateStrategy;
import http.supoort.HttpRequestFactory;
import http.supoort.RequestMapping;
import http.view.FileResourceViewResolver;
import http.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = getPort(args);

        HttpRequestFactory httpRequestFactory = new HttpRequestFactory(new HttpSessionManager(new RandomGenerateStrategy()));
        HttpRequestHandlers httpRequestHandlers = initRequestHandlers();
        ViewResolver viewResolver = initViewResolver();

        ExecutorService es = Executors.newFixedThreadPool(100);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection, httpRequestFactory, httpRequestHandlers, viewResolver));
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
        HttpRequestHandlers httpRequestHandlers = new HttpRequestHandlers(new FileResourceController(RequestMapping.GET("/*")));
        Controller userRequestHandler = new UserController(RequestMapping.GET("/user/create"), RequestMapping.POST("/user/create"));

        httpRequestHandlers.addHandler(userRequestHandler);
        return httpRequestHandlers;
    }

    private static ViewResolver initViewResolver() {
        return new FileResourceViewResolver();
    }

}
