package webserver;

import controller.Controller;
import http.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoggingUtils;
import webserver.exception.RequestHandlingFailException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static http.response.HttpResponse.CRLF;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logConnection();

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handle(in, out);
        } catch (IOException e) {
            LoggingUtils.logStackTrace(logger, e);
            throw new RequestHandlingFailException();
        }
    }

    private void logConnection() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
    }

    private void handle(InputStream in, OutputStream out) throws IOException {
        HttpRequest request = HttpRequestFactory.getHttpRequest(in);
        HttpResponse response = getHttpResponseOf(request);

        logger.debug(request.toString());
        handle(request, response);

        writeResponse(response, out);
    }

    private HttpResponse getHttpResponseOf(HttpRequest request) {
        HttpVersion version = request.getVersion();
        return HttpResponse.of(version);
    }

    private void handle(HttpRequest request, HttpResponse response) {
        if (ControllerMapper.canHandle(request)) {
            Controller controller = ControllerMapper.map(request);
            controller.handle(request, response);
        }
        StaticResourceHandler.forward(request, response);
    }

    private void writeResponse(HttpResponse response, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeBytes(response.getMessageHeader());
        dos.writeBytes(CRLF);
        if (response.hasBody()) {
            dos.write(response.getBody());
        }
    }
}
