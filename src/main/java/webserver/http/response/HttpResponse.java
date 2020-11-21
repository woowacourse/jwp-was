package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import webserver.ClientException;
import webserver.ServerException;
import webserver.controller.ApplicationBusinessException;
import webserver.controller.ContentType;
import webserver.controller.ContentTypeMapper;
import webserver.http.Protocol;

public class HttpResponse {
    private static final String DELIMITER = "\\.";
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
            .error(errorMessage);
    }

    public static Builder internalServerError(ServerException e) {
        return new Builder(e.getStatus())
            .error(e.getMessage());
    }

    public static Builder clientException(ClientException e) {
        return new Builder(e.getStatus())
            .error(e.getMessage());
    }

    public static Builder businessException(ApplicationBusinessException e) {
        return new Builder(e.getStatus())
            .error(e.getMessage());
    }

    public void respond(DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.getValue());
            dos.writeBytes(lineSeparator);
            dos.writeBytes(responseHeader.toValue());
            dos.writeBytes(lineSeparator);
            dos.write(body, 0, body.length);
            dos.close();
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

    public String getCookie() {
        return responseHeader.getCookie();
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

        public Builder bodyByPath(String path) {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            String[] splitPath = path.split(DELIMITER);
            String extension = splitPath[splitPath.length-1];
            ContentType contentType = ContentTypeMapper.map(extension);

            this.contentType(contentType.value())
                .contentLength(body.length);
            this.body = body;
            return this;
        }

        public Builder body(String body) {
            byte[] bodyInBytes = body.getBytes();
            this.contentType(ContentType.HTML.value())
                .contentLength(bodyInBytes.length);
            this.body = bodyInBytes;
            return this;
        }

        public Builder error(String errorMessage) {
            this.body = String.format("error: %s", errorMessage).getBytes(StandardCharsets.UTF_8);
            return this;
        }

        public Builder setCookie(String cookieName, String cookieValue, String path) {
            fields.put("set-Cookie", String.format("%s=%s; Path=%s", cookieName, cookieValue, path));
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this.statusLine, new ResponseHeader(this.fields), this.body);
        }
    }
}
