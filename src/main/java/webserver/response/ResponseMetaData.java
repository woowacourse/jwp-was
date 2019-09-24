package webserver.response;

import utils.FileIoUtils;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static webserver.response.ResponseHeaderFieldKeys.*;

public class ResponseMetaData {

    private static final String VERSION = "HTTP/1.1";
    private static final String HEADER_DELIMITER = ": ";
    public static final String HEADER_NEW_LINE = "\r\n";
    public static final String FIELD_SEPARATOR = ";";
    private static final int MINIMUM_BODY_LENGTH = 0;

    private final HttpRequest httpRequest;
    private final HttpStatus httpStatus;
    private final Map<String, String> responseHeaderFields;

    public ResponseMetaData(final HttpRequest httpRequest, final HttpStatus httpStatus, final Map<String, String> responseHeaderFields) {
        this.responseHeaderFields = responseHeaderFields;
        this.httpRequest = httpRequest;
        this.httpStatus = httpStatus;

        int bodyLength = getBody().length;
        if (existsBody(bodyLength)) {
            appendContentLengthToHeaderFields(bodyLength);
        }
    }

    private boolean existsBody(final int bodyLength) {
        return bodyLength > MINIMUM_BODY_LENGTH;
    }

    private void appendContentLengthToHeaderFields(final int bodyLength) {
        this.responseHeaderFields.put(CONTENT_LENGTH, String.valueOf(bodyLength));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getVersion() {
        return VERSION;
    }

    public byte[] getBody() {
        try {
            return FileIoUtils.loadFileFromClasspath(httpRequest.findFilePath());
        } catch (IOException | URISyntaxException e) {
            return new byte[0];
        }
    }

    public String getHttpResponseHeaderFields() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : responseHeaderFields.entrySet()) {
            builder.append(entry.getKey());
            builder.append(HEADER_DELIMITER);
            builder.append(entry.getValue());
            builder.append(HEADER_NEW_LINE);
        }
        builder.append(HEADER_NEW_LINE);

        return builder.toString();
    }

    public String getResponseLine() {
        HttpStatus httpStatus = getHttpStatus();
        return String.join(" ", getVersion(), String.valueOf(httpStatus.getNumber()), httpStatus.name());
    }

    public static final class Builder {
        private final Map<String, String> responseHeaderFields = new HashMap<>();
        private HttpRequest httpRequest;
        private HttpStatus httpStatus;

        private Builder(HttpRequest httpRequest, HttpStatus httpStatus) {
            this.httpRequest = httpRequest;
            this.httpStatus = httpStatus;
            responseHeaderFields.put(CONTENT_TYPE, "text/html" + FIELD_SEPARATOR + "charset=utf-8");
        }

        public static Builder builder(HttpRequest httpRequest, HttpStatus httpStatus) {
            return new Builder(httpRequest, httpStatus);
        }

        public Builder contentType(String contentType) {
            responseHeaderFields.put(CONTENT_TYPE, contentType);
            return this;
        }

        public Builder contentType(String contentType, String charset) {
            responseHeaderFields.put(CONTENT_TYPE, contentType + FIELD_SEPARATOR + "charset=" + charset);
            return this;
        }

        public Builder location(String location) {
            responseHeaderFields.put(LOCATION, location);
            return this;
        }

        public Builder setCookie(String cookie) {
            responseHeaderFields.put(SET_COOKIE, cookie);
            return this;
        }

        public Builder setCookie(String cookie, String path) {
            responseHeaderFields.put(SET_COOKIE, cookie + FIELD_SEPARATOR + "Path=" + path);
            return this;
        }

        public ResponseMetaData build() {
            return new ResponseMetaData(httpRequest, httpStatus, responseHeaderFields);
        }
    }
}
