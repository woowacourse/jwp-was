package webserver.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.Header;

public class HttpResponse {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final StatusLine statusLine;
    private final Header header;
    private final byte[] body;

    public HttpResponse(StatusLine statusLine, Header header, byte[] body) {
        this.statusLine = statusLine;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse of(String code, Header header, byte[] body) {
        return new HttpResponse(StatusLine.of(code), header, body);
    }

    public static HttpResponse of(String code, Header header) {
        return new HttpResponse(StatusLine.of(code), header, new byte[0]);
    }

    public void respond(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(header.toValue());
            dos.writeBytes(lineSeparator);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getHeader() {
        return header.toValue();
    }

    public byte[] getBody() {
        return body;
    }
}
