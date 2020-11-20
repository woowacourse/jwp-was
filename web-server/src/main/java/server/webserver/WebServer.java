package server.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.web.HandlerMapping;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static <T> void main(Class<T> applicationClass, String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 1L, TimeUnit.SECONDS, queue);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            HandlerMapping handlerMapping = new HandlerMapping(applicationClass);
            while ((connection = listenSocket.accept()) != null) {
                threadPoolExecutor.execute(new RequestHandler(connection, handlerMapping));
            }
        }
    }
}
