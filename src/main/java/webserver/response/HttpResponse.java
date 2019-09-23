package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import static configure.PathConstants.DEFAULT_REDIRECT_LOCATION;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private String location = DEFAULT_REDIRECT_LOCATION;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void makeResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpStatus == HttpStatus.OK) {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.findFilePath());
            response200Header(dos, httpRequest.findContentType(), body.length);
            responseBody(dos, body);
        }
        if (httpStatus == HttpStatus.FOUND) {
            response302Header(dos, location);
        }
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location:" + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setLocation(final String location) {
        this.location = location;
    }
}
