package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.fp.tailrecursion.RecursiveProcedure;
import webserver.http.HttpPort;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final HttpPort DEFAULT_PORT = HttpPort.PORT_8080;


    public static void main(String[] args) throws Exception {
        final HttpPort port = Optional.ofNullable(args).filter(x -> x.length != 0)
                                                        .flatMap(x -> HttpPort.of(x[0]))
                                                        .orElse(DEFAULT_PORT);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (final ServerSocket listener = new ServerSocket(port.number())) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            handleRequest(listener).execute();
        }
    }

    private static RecursiveProcedure handleRequest(ServerSocket listener) {
        final boolean isSuccess = acceptConnection(listener).map(connection -> {
            (new Thread(new RequestHandler(connection))).start();
            return true;
        }).orElse(false);
        return isSuccess ? () -> handleRequest(listener) : RecursiveProcedure.exit();
    }

    private static Optional<Socket> acceptConnection(ServerSocket listener) {
        try {
            return Optional.of(listener.accept());
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}