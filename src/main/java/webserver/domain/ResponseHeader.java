package webserver.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ResponseHeader {
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String NEW_LINE = "\r\n";

    private final String protocol = HTTP_1_1;
    private final HttpStatus httpStatus;
    protected final Map<String, String> responseFields = new HashMap<>();

    protected ResponseHeader(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getFieldValue(final String fieldKey) {
        return this.responseFields.getOrDefault(fieldKey, "");
    }

    // TODO 이름 바꾸자
    public String make() {
        return this.protocol + " " + httpStatus.toString() + NEW_LINE
                + this.responseFields.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(NEW_LINE));
    }

}
