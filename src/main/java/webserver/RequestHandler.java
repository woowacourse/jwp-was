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

import db.DataBase;
import model.User;
import web.HttpMethod;
import web.HttpRequest;
import web.HttpResponse;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.from(br);
            HttpResponse response = new HttpResponse(out);

            String path = getDefaultPath(request.getPath());
            if (HttpMethod.POST == request.getMethod()) {
                if ("/user/create".equals(path)) {
                    User user = User.from(request.getRequestBody());
                    DataBase.addUser(user);
                    response.sendRedirect("/index.html");
                }
            } else if (HttpMethod.NONE == request.getMethod()) {
                response.response405Header();
            } else {
                response.forward(path);
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
