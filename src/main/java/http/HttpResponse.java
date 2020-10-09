package http;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final DataOutputStream dataOutputStream;

    public HttpResponse(final DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.setContentType(contentType);
        httpHeaders.setContentLength(lengthOfBodyContent);
        responseHeader(ResponseStatusLine.OK, httpHeaders);
    }

    public void response302Header(String location) {
        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.setLocation(location);
        responseHeader(ResponseStatusLine.FOUND, httpHeaders);
    }

    public void response404Header() {
        responseHeader(ResponseStatusLine.NOT_FOUND);
    }

    public void response500Header() {
        responseHeader(ResponseStatusLine.INTERNAL_SERVER_ERROR);
    }

    public void ok(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void noContent() {
        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(final ResponseStatusLine responseStatusLine) {
        try {
            dataOutputStream.writeBytes(responseStatusLine.toMessage() + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(final ResponseStatusLine responseStatusLine, final HttpHeaders httpHeaders) {
        try {
            dataOutputStream.writeBytes(responseStatusLine.toMessage() + LINE_SEPARATOR);
            dataOutputStream.writeBytes(httpHeaders.toMessage(LINE_SEPARATOR) + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
