package http.response;

import http.StartLine;
import http.request.HttpVersion;

public class StatusLine implements StartLine {
    private static final String STATUS_LINE_DELIMITER = " ";

    private final HttpVersion httpVersion;
    private final HttpStatus statusCode;

    public StatusLine(HttpStatus statusCode) {
        this.httpVersion = HttpVersion.from(1.1);
        this.statusCode = statusCode;
    }

    @Override
    public String toHttpMessage() {
        return httpVersion.toHttpMessage() + STATUS_LINE_DELIMITER + statusCode.toHttpMessage();
    }
}
