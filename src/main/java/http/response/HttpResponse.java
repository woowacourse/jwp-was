package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final String header;
    private final byte[] body;

    public HttpResponse(String header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public HttpResponse(String header) {
        this(header, null);
    }

    public String getHeader() {
        return this.header;
    }

    public byte[] getBody() {
        return body;
    }

    public void createResponse(DataOutputStream dos) {
        try {
            dos.writeBytes(this.header);
            if (this.body != null) {
                dos.write(this.body, 0, body.length);
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
