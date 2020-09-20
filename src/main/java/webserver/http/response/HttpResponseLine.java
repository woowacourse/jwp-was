package webserver.http.response;

import webserver.http.Protocol;

public class HttpResponseLine {
    private static final Protocol DEFAULT_PROTOCOL = Protocol.HTTP_1_1;
    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public HttpResponseLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    public HttpResponseLine(HttpStatus httpStatus) {
        this(DEFAULT_PROTOCOL, httpStatus);
    }

    public String format() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(protocol.getProtocol());
        stringBuilder.append(" ");
        stringBuilder.append(httpStatus.name());
        stringBuilder.append(" ");
        stringBuilder.append(httpStatus.getStatusCode());

        return stringBuilder.toString();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
