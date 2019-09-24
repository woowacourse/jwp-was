package http.response;

import http.HttpStatus;
import http.HttpVersion;

public class HttpStatusLine {
    private HttpVersion httpVersion = HttpVersion.HTTP_1_1;
    private HttpStatus httpStatus;

    public HttpStatusLine(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus.toString() + "\r\n";
    }
}
