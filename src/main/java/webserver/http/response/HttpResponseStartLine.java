package webserver.http.response;

import webserver.http.HttpVersion;

public class HttpResponseStartLine {
    private HttpVersion httpVersion;
    private HttpStatusCode httpStatusCode;

    public HttpResponseStartLine(HttpVersion httpVersion, HttpStatusCode httpStatusCode) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }

    public static HttpResponseStartLine defaultStartLine() {
        return new HttpResponseStartLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK);
    }

    public void update(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpResponseStartLineString() {
        return httpVersion.getVersion() + " "
                + httpStatusCode.getCode() + " " + httpStatusCode.getMessage() + " ";
    }
}
