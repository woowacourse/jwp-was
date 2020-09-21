package webserver.http.message;

import webserver.http.StartLine;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;

public class HttpMessage {
    private static final String NEW_LINE = System.lineSeparator();

    private final StartLine startLine;
    private final HttpHeader httpHeader;
    private final HttpBody httpBody;

    public HttpMessage(StartLine startLine, HttpHeader httpHeader, HttpBody httpBody) {
        this.startLine = startLine;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public HttpMessage(StartLine startLine, HttpHeader httpHeader) {
        this(startLine, httpHeader, HttpBody.empty());
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    public String toHttpMessage() {
        return startLine.toHttpMessage() + NEW_LINE
                + httpHeader.toHttpMessage() + NEW_LINE
                + NEW_LINE
                + httpBody.toHttpMessage();
    }
}
