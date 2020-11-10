import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Controller;
import dto.UrlMappingCreateDto;

// @RequiredArgsConstructor
public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    private final Controller frontController;

    public WebServer(Controller staticController, List<UrlMappingCreateDto> urlMappingCreateDtos) {
        this.frontController = new FrontController(staticController, UrlMapper.from(urlMappingCreateDtos));
    }

    public void run() throws Exception {
        int port = DEFAULT_PORT;

        ExecutorService es = Executors.newFixedThreadPool(CORES);
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection, frontController));
            }
        }
    }
}
