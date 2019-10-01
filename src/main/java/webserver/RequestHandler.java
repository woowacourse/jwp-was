package webserver;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(request, out);

            if (request.getCookies().getCookie(HttpSessions.SESSION_ID_NAME) == null) {
                response.addHeader("Set-Cookie", HttpSessions.SESSION_ID_NAME + "=" + UUID.randomUUID());
            }

            String path = getDefaultPath(request.getPath());
            Controller controller = RequestMapping.getController(path);
            logger.debug("Controller : {}", controller);
            controller.service(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }
}
