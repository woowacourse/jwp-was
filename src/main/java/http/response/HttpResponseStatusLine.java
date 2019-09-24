package http.response;

import http.HttpStatusCode;
import http.HttpVersion;

public class HttpResponseStatusLine {
    private HttpVersion httpVersion1;
    private String httpVersion;
    private HttpStatusCode httpStatusCode;

    public HttpResponseStatusLine(String line) {
        String[] statusLine = line.split(" ");
        this.httpVersion = statusLine[0];
        this.httpStatusCode = HttpStatusCode.of(statusLine[1]);
    }

    public HttpResponseStatusLine(HttpVersion httpVersion, HttpStatusCode httpStatusCode) {
        this.httpVersion1 = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatusCode.toString() + "\n";
    }
}
