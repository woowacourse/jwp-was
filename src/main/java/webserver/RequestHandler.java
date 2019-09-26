package webserver;

import http.controller.HttpRequestControllers;
import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.session.SessionManager;
import http.supoort.converter.HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final HttpRequestControllers httpRequestControllers;
    private final SessionManager sessionManager;
    private final HttpMessageConverter converter;

    public RequestHandler(Socket connection, HttpRequestControllers httpRequestControllers,
                          SessionManager sessionManager, HttpMessageConverter converter) {
        this.connection = connection;
        this.httpRequestControllers = httpRequestControllers;
        this.sessionManager = sessionManager;
        this.converter = converter;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handleRequest(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new FailToHandleRequestException(e.getMessage());
        }
    }

    private void handleRequest(InputStream in, OutputStream out) {
        try {
            ServletRequest request = getRequest(in);
            ServletResponse response = getResponse(out);

            httpRequestControllers.doService(request, response);

            converter.response(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            sendError(out);
        }
    }

    private ServletResponse getResponse(OutputStream out) {
        return new ServletResponse(out);
    }

    private ServletRequest getRequest(InputStream in) {
        ServletRequest request = converter.parse(in);
        request.bindSessionManager(this.sessionManager);
        return request;
    }

    private void sendError(OutputStream out) {
        ServletResponse response = new ServletResponse(out);
        response.error();
        converter.response(response);
    }
}
