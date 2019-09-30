package http.response;

import http.HttpVersion;

public class HttpStatusLine {
    private HttpVersion version;
    private HttpStatus status;

    HttpStatusLine(HttpVersion version, HttpStatus status) {
        this.version = version;
        this.status = status;
    }

    @Override
    public String toString() {
        return version + " " + status.getMessage();
    }
}
