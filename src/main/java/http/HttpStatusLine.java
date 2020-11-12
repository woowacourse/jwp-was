package http;

public class HttpStatusLine {
    private HttpStatus httpStatus;
    private final String httpVersion;

    private HttpStatusLine(HttpStatus httpStatus, String httpVersion) {
        this.httpStatus = httpStatus;
        this.httpVersion = httpVersion;
    }

    public static HttpStatusLine of(HttpStatus status, String httpVersion) {
        return new HttpStatusLine(status, httpVersion);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getLine() {
        return httpVersion + " " + httpStatus.getStatusCode() + " " + httpStatus.getMessage();
    }
}
