package webserver.domain;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";

    private String protocol;
    private HttpStatus httpStatus;
    private Map<String, String> responseFields;

    ResponseHeader(final String protocol, final HttpStatus httpStatus,
                   final Map<String, String> responseFields) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.responseFields = responseFields;
    }

    public String getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getResponseFields() {
        return Collections.unmodifiableMap(this.responseFields);
    }

    public String getFieldValue(final String fieldKey) {
        return this.responseFields.getOrDefault(fieldKey, "");
    }

    // TODO 이름 바꾸자
    String make(final int contentLength) {
        this.responseFields.put("Content-Length", Integer.toString(contentLength));
        return this.protocol + " " + httpStatus.toString() + NEW_LINE
                + this.responseFields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(NEW_LINE));
    }
}
