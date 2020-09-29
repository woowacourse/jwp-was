package jwp.was.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jwp.was.webapplicationserver.configure.controller.ControllerMapper;
import jwp.was.webserver.handler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = calculatePort(args);

        // 쓰레드를 생성한다.
        ExecutorService es = Executors.newFixedThreadPool(10);

        // 서버소켓을 생성한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            LOGGER.info("Web Application Server started {} port.", port);
            startConnectionThread(listenSocket, es);
        }
    }

    private static int calculatePort(String[] args) {
        // 웹서버는 기본적으로 8080번 포트를 사용한다.
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        // 포트를 입력받는 경우 입력받은 포트를 사용한다.
        return Integer.parseInt(args[0]);
    }

    private static void startConnectionThread(ServerSocket listenSocket, ExecutorService es)
        throws IOException {
        // 중복된 RequestMapping이 있는지 확인한다.
        ControllerMapper.getInstance();

        // 클라이언트가 연결될때까지 대기한다.
        LOGGER.info("대기중");
        Socket connection;
        while ((connection = listenSocket.accept()) != null) {
            Socket threadConnection = connection;
            es.execute(() -> {
                RequestHandler requestHandler = new RequestHandler(threadConnection);
                requestHandler.run();
                LOGGER.info("실행 connection : {}", threadConnection.toString());
            });
        }
    }
}
