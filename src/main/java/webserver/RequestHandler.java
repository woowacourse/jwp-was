package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.LoginController;
import controller.UserController;
import http.HttpRequest;
import http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final FrontController frontController;

    static {
        List<HandlerMapping> handlerMappings = new ArrayList<>();
        handlerMappings.add(new ResourceHandlerMapping());
        handlerMappings.add(new UrlHandlerMapping("/user/create", new UserController()));
        handlerMappings.add(new UrlHandlerMapping("/user/login", new LoginController()));
        frontController = new FrontController(handlerMappings);
    }

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             OutputStream outputStream = connection.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            HttpRequest httpRequest = HttpRequest.from(bufferedReader);
            logger.info("Request: {}", httpRequest.getRequestLine());
            HttpResponse httpResponse = new HttpResponse(dataOutputStream);
            frontController.service(httpRequest, httpResponse);
        } catch (Exception exception) {
            logger.error("Unhandled exception occur. ", exception);
        }
    }
}
