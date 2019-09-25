package webserver.domain;

import java.util.Map;

public class ResponseHeader {
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\r\n";
    private static final String KEY_VALUE_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int DEFAULT_HEADER_CAPACITY = 256; // 처리성능 향상을 위해 StringBuilder에서 사용할 array 공간을 미리 확보

    private HttpVersion protocol;
    private HttpStatus httpStatus;
    private Map<String, String> responseFields;

    ResponseHeader(final HttpVersion protocol,
                   final HttpStatus httpStatus,
                   final Map<String, String> responseFields) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.responseFields = responseFields;
    }

    public HttpVersion getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getResponseFields() {
        return this.responseFields;
    }

    public String getFieldValue(final String fieldKey) {
        return this.responseFields.getOrDefault(fieldKey, EMPTY);
    }

    String make(final int contentLength) {
        setContentLength(contentLength);
        final StringBuilder builder = makeFirstLine();
        addRestLine(builder);
        return builder.toString();
    }

    private void setContentLength(final int contentLength) {
        this.responseFields.put(CONTENT_LENGTH, Integer.toString(contentLength));
    }

    private StringBuilder makeFirstLine() {
        return new StringBuilder(DEFAULT_HEADER_CAPACITY)
                .append(this.protocol.toString())
                .append(SPACE)
                .append(httpStatus.toString())
                .append(NEW_LINE);
    }

    private void addRestLine(final StringBuilder builder) {
        for (final String key: this.responseFields.keySet()) {
            builder.append(key)
                    .append(KEY_VALUE_DELIMITER)
                    .append(this.responseFields.get(key))
                    .append(NEW_LINE);
        }
    }
}
