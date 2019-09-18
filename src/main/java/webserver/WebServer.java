package webserver;

import http.controller.FileResourceRequestHandler;
import http.controller.HttpRequestHandler;
import http.controller.HttpRequestHandlers;
import http.controller.UserRequestHandler;
import http.model.HttpMethod;
import http.supoort.RequestMapping;
import http.view.ModelResolver;
import http.view.ViewHandler;
import http.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        HttpRequestHandlers httpRequestHandlers = new HttpRequestHandlers();
        HttpRequestHandler fileHandler = new FileResourceRequestHandler();
        HttpRequestHandler userRequestHandler = new UserRequestHandler();

        httpRequestHandlers.addHandler(new RequestMapping(HttpMethod.GET, "/*"), fileHandler);
        httpRequestHandlers.addHandler(new RequestMapping(HttpMethod.GET, "/user/create"), userRequestHandler);
        httpRequestHandlers.addHandler(new RequestMapping(HttpMethod.POST, "/user/create"), userRequestHandler);


        ViewHandler viewHandler = new ViewHandler();
        viewHandler.addResolver(new ViewResolver());
        viewHandler.addResolver(new ModelResolver());

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, httpRequestHandlers, viewHandler));
                thread.start();
            }
        }
    }
}
