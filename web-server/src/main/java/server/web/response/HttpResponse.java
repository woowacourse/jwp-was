package server.web.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.web.HeaderName;
import server.web.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = "\r\n";
    private static final String SPACE = " ";
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final DataOutputStream dataOutputStream;

    private final HttpHeader httpHeader;

    public HttpResponse(DataOutputStream dataOutputStream, HttpHeader httpHeader) {
        this.dataOutputStream = dataOutputStream;
        this.httpHeader = httpHeader;
    }

    public void putHeader(String key, String value) {
        this.httpHeader.put(key, value);
    }

    public void putHeader(HeaderName headerName, String value) {
        this.putHeader(headerName.getName(), value);
    }

    public void response(HttpStatusCode statusCode) {
        try {
            this.dataOutputStream.writeBytes(HTTP_VERSION + SPACE + statusCode.getValue() + NEW_LINE);
            this.dataOutputStream.writeBytes(this.httpHeader.write());
            this.dataOutputStream.writeBytes(NEW_LINE);

            this.dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response(HttpStatusCode statusCode, byte[] body) {
        try {
            this.dataOutputStream.writeBytes(HTTP_VERSION + SPACE + statusCode.getValue() + NEW_LINE);
            this.dataOutputStream.writeBytes(this.httpHeader.write());
            this.dataOutputStream.writeBytes(NEW_LINE);

            this.dataOutputStream.write(body, 0, body.length);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String url) {
        putHeader(HeaderName.LOCATION, url);
        response(HttpStatusCode.FOUND);
    }

    public void addSession(String session) {
        this.httpHeader.addSession(session);
    }
}
