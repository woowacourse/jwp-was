package http.response;

public class HttpResponseLine {

    private static final String HTTP_VERSION = "HTTP/1.1";

    private final String version;
    private HttpStatus httpStatus;

    public HttpResponseLine() {
        this.version = HTTP_VERSION;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public String getVersion() {
        return version;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
