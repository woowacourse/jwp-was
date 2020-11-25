import static org.slf4j.LoggerFactory.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

public class WebServer {
    private static final Logger logger = getLogger(WebServer.class);
    private static final ExecutorService es = Executors.newFixedThreadPool(100);
    private static final int DEFAULT_PORT = 8080;

    public void run(String[] args) throws Exception {
        int port = 0;
        if (Objects.isNull(args) || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection));
            }
        }
    }
}
