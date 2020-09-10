package webserver.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final StatusLine statusLine;
    private final String header;
    private final byte[] body;

    public HttpResponse(StatusLine statusLine, String header, byte[] body) {
        this.statusLine = statusLine;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse of(String code, byte[] body) {
        String header = "Content-Type: text/html;charset=utf-8" + lineSeparator
            + "Content-Length: " + body.length + lineSeparator;
        return new HttpResponse(StatusLine.of(code), header, body);
    }

    public void respond(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(header);
            dos.writeBytes(lineSeparator);
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
        return header;
    }

    public byte[] getBody() {
        return body;
    }
}
