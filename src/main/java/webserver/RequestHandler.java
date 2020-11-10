package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.DefaultController;
import webserver.controller.ListUserController;
import webserver.controller.LoginController;
import webserver.controller.RequestHandlerMapping;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestHandlerMapping requestHandlerMapping;


    public RequestHandler(Socket connectionSocket, RequestHandlerMapping requestHandlerMapping) {
        this.connection = connectionSocket;
        initRequestHandler(requestHandlerMapping);
        this.requestHandlerMapping = requestHandlerMapping;

    }

    private void initRequestHandler(RequestHandlerMapping requestHandlerMapping) {
        requestHandlerMapping.putController("/user/list.html", new ListUserController());
        requestHandlerMapping.putController("/user/create", new CreateUserController());
        requestHandlerMapping.putController("/user/login", new LoginController());
        requestHandlerMapping.putController("/", DefaultController.getInstance());
    }

    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection
            .getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(out);

            Controller controller = requestHandlerMapping.getController(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
