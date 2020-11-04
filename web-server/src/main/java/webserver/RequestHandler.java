package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.StaticFile;
import web.controller.Controller;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.session.HttpSession;

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

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            if (request.getCookies().getCookie(HttpSession.SESSION_ID) == null) {
                String uuid = String.valueOf(UUID.randomUUID());
                request.addCookie(HttpSession.SESSION_ID, uuid);
                response.addHeader("Set-Cookie", HttpSession.SESSION_ID + "=" + uuid);
            }

            Controller controller = RequestMapping.getController(request.getPath());
            if (Objects.isNull(controller)) {
                String location = getDefaultPath(request.getPath());
                response.ok(StaticFile.of(location).getPrefix() + location);
            } else {
                controller.service(request, response);
            }
            response.send();
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
