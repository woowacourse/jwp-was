package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        final int port = Optional.ofNullable(args).filter(x -> x.length != 0)
                                                    .map(x -> Integer.parseInt(x[0]))
                                                    .orElse(DEFAULT_PORT);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (final ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            connect(listenSocket);
        }
    }

    private static Socket connect(ServerSocket listenSocket) {
        return accept(listenSocket).map(x -> {
            new Thread(new RequestHandler(x)).start();
            return connect(listenSocket);
        }).orElse(null);
    }

    private static Optional<Socket> accept(ServerSocket listenSocket) {
        try {
            return Optional.of(listenSocket.accept());
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}