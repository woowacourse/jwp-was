package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.DefaultController;
import webserver.controller.ListUserController;
import webserver.controller.LoginController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Controller> controllerMapper;


    public RequestHandler(Socket connectionSocket, Map<String, Controller> controllerMapper) {
        this.connection = connectionSocket;
        this.controllerMapper = controllerMapper;

        initControllerMapper(controllerMapper);
    }

    private void initControllerMapper(Map<String, Controller> controllerMapper) {
        controllerMapper.put("/user/create", new CreateUserController());
        controllerMapper.put("/user/list.html", new ListUserController());
        controllerMapper.put("/user/login", new LoginController());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(out);

            Controller controller = controllerMapper.getOrDefault(httpRequest.getPath(), new DefaultController());
            controller.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
