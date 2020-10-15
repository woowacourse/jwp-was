package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = "\r\n";

    private final DataOutputStream dataOutputStream;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200(final byte[] body, final String contentType) {
        response200Header(body.length, contentType);
        responseBody(body);
    }

    private void response200Header(int contentLength, String contentType) {
        try {
            this.dataOutputStream.writeBytes(HttpVersion.HTTP_1_1.getVersion() + HttpStatusCode.OK.getValue() + NEW_LINE);
            this.dataOutputStream.writeBytes(HeaderName.CONTENT_TYPE.getHeader(contentType) + NEW_LINE);
            this.dataOutputStream.writeBytes(HeaderName.CONTENT_LENGTH.getHeader(contentLength) + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            this.dataOutputStream.write(body, 0, body.length);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302(final String url) {
        try {
            this.dataOutputStream.writeBytes(HttpVersion.HTTP_1_1.getVersion() + HttpStatusCode.FOUND.getValue() + NEW_LINE);
            this.dataOutputStream.writeBytes(HeaderName.LOCATION.getHeader(url) + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
