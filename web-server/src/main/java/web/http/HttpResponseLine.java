package web.http;

public class HttpResponseLine {
    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public HttpResponseLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    public HttpResponseLine(HttpStatus httpStatus) {
        this(Protocol.getDefaultProtocol(), httpStatus);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
