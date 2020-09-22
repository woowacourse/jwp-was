package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.UserCreateController;
import webserver.middleware.Middlewares;
import webserver.middleware.NotFound;
import webserver.middleware.ServeStatic;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Middlewares middlewares = new Middlewares()
        .chain(new ServeStatic("templates"))
        .chain(new ServeStatic("static"))
        .chain("/user/create", new UserCreateController())
        .chain(new NotFound());

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = HttpResponse.from(out, request.version());
            middlewares.run(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
