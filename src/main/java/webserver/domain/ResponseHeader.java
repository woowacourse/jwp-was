package webserver.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";

    private String protocol;
    private HttpStatus httpStatus;
    private Map<String, String> responseFields;

    private ResponseHeader(final String protocol, final HttpStatus httpStatus,
                           final Map<String, String> responseFields) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.responseFields = Collections.unmodifiableMap(responseFields);
    }

    public static class Builder {
        private static final String HTTP_1_1 = "HTTP/1.1";

        private String protocol = HTTP_1_1;
        private HttpStatus httpStatus = HttpStatus.OK;
        private Map<String, String> responseFields = new HashMap<>();

        public Builder(final String protocol, final HttpStatus httpStatus) {
            this.protocol = protocol;
            this.httpStatus = httpStatus;
        }

        public Builder protocol(final String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder httpStatus(final HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder putField(final String fieldName, String fieldValue) {
            this.responseFields.put(fieldName, fieldValue);
            return this;
        }

        public Builder redirectUrl(final String url) {
            this.httpStatus = HttpStatus.FOUND;
            putField("Location", url);
            return this;
        }

        public ResponseHeader build() {
            return new ResponseHeader(this.protocol, this.httpStatus, this.responseFields);
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getResponseFields() {
        return responseFields;
    }

    // TODO 이름 바꾸자
    public String make() {
        return this.protocol + " " + httpStatus.toString() + NEW_LINE
                + this.responseFields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(NEW_LINE));
    }
}
