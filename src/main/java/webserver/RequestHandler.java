package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.UserCreateController;
import webserver.filter.Filter;
import webserver.filter.ServeStatic;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final List<Filter> FILTERS = Arrays.asList(
        new ServeStatic("templates"),
        new ServeStatic("static")
    );
    private static final List<Controller> CONTROLLERS = Arrays.asList(
        new UserCreateController()
    );


    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection
            .getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = HttpResponse.from(out, request.version());
            new ApplicationFilterChain(FILTERS, CONTROLLERS).run(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
