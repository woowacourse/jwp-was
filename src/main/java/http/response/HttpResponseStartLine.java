package http.response;

import http.StatusCode;

public class HttpResponseStartLine {
    private StatusCode statusCode;
    private String version;

    public HttpResponseStartLine(StatusCode statusCode, String version) {
        this.statusCode = statusCode;
        this.version = version;
    }

    @Override
    public String toString() {
        return version + " "
                + statusCode.getStatusValue() + " "
                + statusCode.name();
    }
}
