package webserver.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.Protocol;

public class HttpResponse {
    private static final String lineSeparator = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private final byte[] body;

    private HttpResponse(StatusLine statusLine, ResponseHeader responseHeader, byte[] body) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public static Builder ok() {
        return new Builder(StatusCode.OK);
    }

    public static Builder created(String location) {
        return new Builder(StatusCode.CREATED)
            .location(location);
    }

    public static Builder found(String location) {
        return new Builder(StatusCode.FOUND)
            .location(location);
    }

    public static Builder badRequest(String errorMessage) {
        return new Builder(StatusCode.BAD_REQUEST)
            .body(String.format("error: %s", errorMessage));
    }

    public void respond(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(responseHeader.toValue());
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
        return responseHeader.toValue();
    }

    public byte[] getBody() {
        return body;
    }

    public static class Builder {
        private final StatusLine statusLine;
        private Map<String, String> fields;
        private byte[] body;

        public Builder(StatusCode statusCode) {
            this.statusLine = new StatusLine(Protocol.ONE_POINT_ONE, statusCode);
            this.fields = new HashMap<>();
            this.body = new byte[0];
        }

        public Builder contentLength(int contentLength) {
            fields.put("Content-Length", String.valueOf(contentLength));
            return this;
        }

        public Builder contentType(String contentType) {
            fields.put("Content-Type", contentType);
            return this;
        }

        public Builder location(String location) {
            fields.put("Location", location);
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder body(String body) {
            this.body = body.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this.statusLine, new ResponseHeader(this.fields), this.body);
        }
    }
}
