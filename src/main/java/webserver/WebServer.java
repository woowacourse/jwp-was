package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int N_THREADS = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

        int port = Optional.ofNullable(args)
            .filter(WebServer::isArgsNotEmpty)
            .map(el -> Integer.parseInt(el[0]))
            .orElse(DEFAULT_PORT);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while (Objects.nonNull(connection = listenSocket.accept())) {
                executorService.execute(new RequestHandler(connection));
            }
        }
    }

    private static boolean isArgsNotEmpty(String[] args) {
        return args.length != 0;
    }
}
