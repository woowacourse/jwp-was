package webserver;

import static controller.UserCreateController.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.MimeType;
import utils.FileIoUtils;

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
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out, httpRequest.getVersion());

            MimeType mimeType = httpRequest.getMimeType();
            String path = httpRequest.getPath();
            byte[] body = FileIoUtils.loadFileFromClasspath(path);

            if (httpRequest.matchMethod(HttpMethod.GET)) {
                httpResponse.response200Header(body.length, mimeType.getContentType());
            }
            if (httpRequest.matchMethod(HttpMethod.POST)) {
                createUser(httpRequest);
                httpResponse.setHttpStatus(HttpStatus.FOUND);
                httpResponse.response302Header(body.length, mimeType.getContentType(), "/index.html");
            }
            httpResponse.responseBody(body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
