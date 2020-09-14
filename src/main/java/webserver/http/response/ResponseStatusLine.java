package webserver.http.response;

public class ResponseStatusLine {
    private String httpVersion;
    private HttpStatus httpStatus;

    public ResponseStatusLine(String httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
