package webserver;

import controller.Controller;
import http.request.HttpRequest;
import http.request.HttpRequestCreator;
import http.request.exception.InvalidHttpRequestException;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.response.exception.HttpVersionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ResourceNotFoundException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final ResourceHttpRequestHandler resourceHttpRequestHandler = ResourceHttpRequestHandler.getInstance();
    private final HandlerMapping handlerMapping = HandlerMapping.getInstance();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        //TODO : IOException 처리
        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestCreator.create(inputStream);
            HttpResponse httpResponse = new HttpResponse(httpRequest);

            handleRequest(httpRequest, httpResponse);

            sendResponse(outputStream, httpResponse);
        } catch (IOException | InvalidHttpRequestException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            if (resourceHttpRequestHandler.canHandle(httpRequest.getPath())) {
                resourceHttpRequestHandler.handleHttpRequest(httpRequest, httpResponse);
                return;
            }

            Controller controller = handlerMapping.getHandler(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.NOT_FOUND);
        } catch (HttpVersionNotSupportedException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.HTTP_VERSION_NOT_SUPPORTED);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendResponse(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);

        try {
            byte[] response = httpResponse.serialize();
            dos.write(response, 0, response.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
