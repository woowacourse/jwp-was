package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.HttpRequest;
import web.HttpResponse;
import web.controller.Controller;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = new HttpResponse(out);

            Controller controller = RequestMapping.getController(getDefaultPath(request.getPath()));
            if (Objects.isNull(controller)) {
                response.forward(request.getPath());
            } else {
                controller.service(request, response);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getDefaultPath(String path) {
        if ("/".equals(path)) {
            return "/index.html";
        }
        return path;
    }
}
