package http;

public class HttpStatusLine {
    private final HttpStatus httpStatus;
    private final String httpVersion;

    private HttpStatusLine(HttpStatus httpStatus, String httpVersion) {
        this.httpStatus = httpStatus;
        this.httpVersion = httpVersion;
    }

    public static HttpStatusLine of(HttpStatus status, String httpVersion) {
        return new HttpStatusLine(status, httpVersion);
    }

    public String getLine() {
        return httpVersion + " " + httpStatus.getStatusCode() + " " + httpStatus.name();
    }
}
