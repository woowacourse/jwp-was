package http.model;

public class StatusLine {
    private final HttpProtocols httpProtocols;
    private final HttpStatus httpStatus;

    public StatusLine(HttpProtocols httpProtocols, HttpStatus httpStatus) {
        this.httpProtocols = httpProtocols;
        this.httpStatus = httpStatus;
    }

    public HttpProtocols getHttpProtocols() {
        return httpProtocols;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
