package webserver;

import com.google.common.collect.Maps;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.RequestHandlerMapping;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_THREAD_COUNT = 100;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT);

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                try {
                    Future<?> future = executorService.submit(new RequestHandler(connection, new RequestHandlerMapping(Maps.newHashMap())));
                    future.get();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
