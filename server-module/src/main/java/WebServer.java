import domain.controller.Controller;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebServer {

    private static final int DEFAULT_PORT = 8080;
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args, Controller controller) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        ExecutorService es = Executors.newFixedThreadPool(CORES);
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection, controller));
            }
        }
    }
}
