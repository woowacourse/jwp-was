package webserver;

import controller.Controller;
import controller.exception.ControllerNotFoundException;
import http.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoggingUtils;

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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.getHttpRequest(in);
            logger.debug(request.toString());

            HttpResponse response = responseOf2(request);

            writeResponse(response, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
            LoggingUtils.logStackTrace(logger, e);
        }
    }

    private HttpResponse responseOf2(HttpRequest request) {
        HttpVersion version = request.getVersion();
        HttpResponse response = HttpResponse.of(version);

        // TODO: 2019-10-08 이 부분 예외 발생 너무 과하다.
        try {
            Controller controller = ControllerMapper.map(request);
            controller.handle(request, response);
        } catch (ControllerNotFoundException e) {
            StaticResourceHandler.forward(request, response);
        }
        return response;
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
