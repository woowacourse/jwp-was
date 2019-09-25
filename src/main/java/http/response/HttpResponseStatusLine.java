package http.response;

import http.HttpStatusCode;
import http.HttpVersion;

public class HttpResponseStatusLine {
    private HttpVersion httpVersion;
    private HttpStatusCode httpStatusCode;

    public HttpResponseStatusLine(HttpVersion httpVersion, HttpStatusCode httpStatusCode) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatusCode.toString() + "\n";
    }
}
