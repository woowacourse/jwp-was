package webserver;

import http.controller.HttpRequestHandlers;
import http.model.HttpRequest;
import http.model.HttpResponse;
import http.supoort.HttpErrorResponse;
import http.supoort.HttpRequestFactory;
import http.supoort.ResponseMessageConverter;
import http.view.ModelAndView;
import http.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpRequestFactory httpRequestFactory;
    private HttpRequestHandlers httpRequestHandlers;
    private ViewResolver viewResolver;

    public RequestHandler(Socket connection, HttpRequestFactory httpRequestFactory,
                          HttpRequestHandlers httpRequestHandlers, ViewResolver viewResolver) {
        this.connection = connection;
        this.httpRequestFactory = httpRequestFactory;
        this.httpRequestHandlers = httpRequestHandlers;
        this.viewResolver = viewResolver;
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
            HttpRequest request = httpRequestFactory.getRequest(in);
            ModelAndView modelAndView = httpRequestHandlers.doService(request);

            response(viewResolver.resolve(modelAndView), out);

        } catch (Exception e) {
            logger.error(e.getMessage());
            sendError(e.getMessage(), out);
        }
    }

    private void sendError(String message, OutputStream out) {
        response(HttpErrorResponse.generate(message), out);
    }

    private void response(HttpResponse response, OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        ResponseMessageConverter.convert(response, dos);
    }
}
