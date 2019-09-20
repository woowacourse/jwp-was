package http.response;

import http.HttpVersion;

public class HttpStatusLine {
    private HttpVersion version;
    private HttpStatus status;

    public HttpStatusLine(HttpVersion version, HttpStatus status) {
        this.version = version;
        this.status = status;
    }

    public void changeStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return version + " " + status.getMessage();
    }
}
