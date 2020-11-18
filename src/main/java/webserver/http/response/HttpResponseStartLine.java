package webserver.http.response;

import webserver.http.HttpVersion;

public class HttpResponseStartLine {
    private final HttpVersion httpVersion;
    private final HttpStatusCode httpStatusCode;

    public HttpResponseStartLine(HttpVersion httpVersion, HttpStatusCode httpStatusCode) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String toString() {
        return httpVersion.getVersion() + " "
                + httpStatusCode.getCode() + " " + httpStatusCode.getMessage() + " ";
    }
}
