package http.response;

import http.HttpStatus;

public class HttpStatusLine {
    private String httpVersion;
    private HttpStatus httpStatus;

    public HttpStatusLine(String line) {
        String[] statusLine = line.split(" ");
        this.httpVersion = statusLine[0];
        this.httpStatus = HttpStatus.of(statusLine[1]);
    }

    @Override
    public String toString() {
        return httpVersion + " " + httpStatus.toString() + "\n";
    }
}
