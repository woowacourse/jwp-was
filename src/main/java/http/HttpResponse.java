package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    public static final String NEW_LINE = System.lineSeparator();

    private final DataOutputStream dos;
    private final ResponseHeader responseHeader = new ResponseHeader();

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void addHeader(String key, String value) {
        this.responseHeader.addHeader(key, value);
    }

    public void response(HttpStatus status) {
        responseStatusLine(status);
        responseHeader();
    }

    public void response(HttpStatus status, ResourceType resourceType, byte[] body) {
        responseHeader.addHeader("Content-Length", String.valueOf(body.length));
        responseHeader.addHeader("Content-Type", resourceType.getContentType());

        responseStatusLine(status);
        responseHeader();
        responseBody(body);
    }

    private void responseStatusLine(HttpStatus status) {
        try {
            dos.writeBytes("HTTP/1.1 " + status.getValue() + " " + status.getReasonPhrase() + NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader() {
        String headers = this.responseHeader.readHeaders();
        try {
            dos.writeBytes(headers);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.writeBytes(NEW_LINE);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
