package webserver;

import application.controller.UserController;
import controller.Controller;
import controller.ControllerMapper;
import controller.StaticFileController;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final ControllerMapper controllerMapper = new ControllerMapper();
    private static final Map<Class<?>, Controller> controllers;

    private Socket connection;

    static {
        Map<Class<?>, Controller> controllersMap = new HashMap<>();

        controllersMap.put(StaticFileController.class, new StaticFileController());
        controllersMap.put(UserController.class, new UserController());

        controllers = Collections.unmodifiableMap(controllersMap);
    }

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream()
        ) {
            HttpRequest httpRequest = HttpRequest.readHttpRequest(in);
            logger.info("Receive HttpRequest\n{}", httpRequest.toString());

            Controller controller = findController(httpRequest);
            HttpResponse response = controller.service(httpRequest);

            logger.info("HttpResponse to send\n{}", response.toString());
            DataOutputStream dos = new DataOutputStream(out);
            writeResponseOnOutputStream(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Controller findController(HttpRequest httpRequest) {
        Class<?> controllerClass = controllerMapper.findController(httpRequest);
        return controllers.get(controllerClass);
    }

    private void writeResponseOnOutputStream(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(response.buildHeader());

            byte[] body = response.getBody();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
