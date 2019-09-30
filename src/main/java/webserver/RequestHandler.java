package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestCreator;
import http.request.exception.InvalidHttpRequestException;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httphandler.HttpRequestHandler;
import webserver.httphandler.HttpRequestHandlerMapping;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final HttpRequestHandlerMapping httpRequestHandlerMapping = HttpRequestHandlerMapping.getInstance();
    private Socket connection;

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestCreator.create(inputStream);
            HttpResponse httpResponse = new HttpResponse(httpRequest);

            HttpRequestHandler httpRequestHandler = httpRequestHandlerMapping.getHandler(httpRequest.getPath());
            httpRequestHandler.handleHttpRequest(httpRequest, httpResponse);

            sendResponse(outputStream, httpResponse);
        } catch (IOException | InvalidHttpRequestException e) {
            log.error(e.getMessage(), e.getCause());
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
