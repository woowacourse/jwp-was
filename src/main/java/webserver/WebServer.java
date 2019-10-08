package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.env.Env;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public final class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final Port DEFAULT_PORT = Port.PORT_8080;

    public static void main(String[] args) {
        final Port port = Optional.ofNullable(args).filter(x -> x.length != 0)
                .flatMap(x -> Port.of(x[0]))
                .orElse(DEFAULT_PORT);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (final ServerSocket listener = new ServerSocket(port.number())) {
            logger.info("Web Application Server started {} port.", port);

            Env.init();
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listener.accept()) != null) {
                new Thread(new RequestHandler(connection)).start();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}