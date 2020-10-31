package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = System.lineSeparator();

    private final DataOutputStream dos;
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void response(HttpStatus status) {
        responseStatusLine(status);
        responseHeader();
    }

    public void response(HttpStatus status, byte[] body) {
        responseStatusLine(status);
        responseHeader();
        responseBody(body);
    }

    public void responseStatusLine(HttpStatus status) {
        try {
            dos.writeBytes("HTTP/1.1 " + status.getValue() + " " + status.getReasonPhrase() + NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseHeader() {
        Set<String> keys = this.headers.keySet();
        for (String key : keys) {
            try {
                dos.writeBytes(key + ": " + this.headers.get(key) + NEW_LINE);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
