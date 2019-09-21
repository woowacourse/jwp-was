package webserver.domain.response;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;
    private Map<String, String> responseFields;

    ResponseHeader(final HttpVersion httpVersion, final HttpStatus httpStatus,
                   final Map<String, String> responseFields) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.responseFields = responseFields;
    }

    public String getHttpVersion() {
        return httpVersion.getVersion();
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

    String makeHeaderLine(final int contentLength) {
        this.responseFields.put("Content-Length", Integer.toString(contentLength));
        return this.httpVersion.getVersion() + " " + httpStatus.toString() + NEW_LINE
                + this.responseFields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(NEW_LINE));
    }
}
