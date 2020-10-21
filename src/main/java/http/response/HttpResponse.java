package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import utils.FileIoUtils;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void responseResource(HttpRequest request, String resourcePath,
            String contentType) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resourcePath + request.getPath());
            response200Header(request, contentType, body.length);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseRedirect(HttpRequest request, String location, String contentType) {
        try {
            dos.writeBytes(String.format("%s %d %s \r\n", request.getHttpVersion(),
                    HttpStatus.FOUND.getValue(), HttpStatus.FOUND.getReasonPhrase()));
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(HttpRequest request, String contentType, int contentLength) {
        try {
            dos.writeBytes(String.format("%s %d %s \r\n", request.getHttpVersion(),
                    HttpStatus.OK.getValue(), HttpStatus.OK.getReasonPhrase()));
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + contentLength + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
