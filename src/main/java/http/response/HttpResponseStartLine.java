package http.response;

import http.request.HttpVersion;

public class HttpResponseStartLine {
    private StatusCode statusCode;
    private HttpVersion version;

    public HttpResponseStartLine(StatusCode statusCode, HttpVersion version) {
        this.statusCode = statusCode;
        this.version = version;
    }

    public String convertLineToString() {
        return version.getVersion() + " "
                + statusCode.getStatusValue() + " "
                + statusCode.getStatus();
    }
}
