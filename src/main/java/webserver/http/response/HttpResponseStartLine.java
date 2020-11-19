package webserver.http.response;

import webserver.http.HttpVersion;

public class HttpResponseStartLine {
    private final HttpVersion httpVersion;
    private final HttpStatusCode httpStatusCode;

    public HttpResponseStartLine(HttpVersion httpVersion, HttpStatusCode httpStatusCode) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }

    public static HttpResponseStartLine defaultStartLine() {
        return new HttpResponseStartLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK);
    }

    @Override
    public String toString() {
        return httpVersion.getVersion() + " "
                + httpStatusCode.getCode() + " " + httpStatusCode.getMessage() + " ";
    }
}
