package dev.luffy.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import dev.luffy.http.RequestMapper;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;

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
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.pathHasExtension()) {
                httpResponse.staticFileResource(httpRequest);
                return;
            }

            Method controllerMethod = RequestMapper.get(httpRequest.getPath());

            if (controllerMethod == null) {
                httpResponse.send404();
                return;
            }

            controllerMethod.invoke(null, httpRequest, httpResponse);

        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
    }
}
