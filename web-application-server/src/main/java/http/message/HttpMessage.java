package http.message;

import http.StartLine;
import http.body.HttpBody;
import http.header.HttpHeaders;

public class HttpMessage {
    private static final String NEW_LINE = System.lineSeparator();

    private final StartLine startLine;
    protected final HttpHeaders httpHeaders;
    private final HttpBody httpBody;

    public HttpMessage(StartLine startLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.startLine = startLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    public String toHttpMessage() {
        return startLine.toHttpMessage() + NEW_LINE
                + httpHeaders.toHttpMessage() + NEW_LINE
                + NEW_LINE
                + httpBody.toHttpMessage();
    }
}
