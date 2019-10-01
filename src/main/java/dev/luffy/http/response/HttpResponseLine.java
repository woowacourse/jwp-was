package dev.luffy.http.response;

import dev.luffy.http.HttpProtocol;

public class HttpResponseLine {

    private static final String COMBINE_FORMAT = "%s %s \r\n";

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;

    public HttpResponseLine() {
        this(HttpStatus.OK);
    }

    public HttpResponseLine(HttpStatus httpStatus) {
        this(HttpProtocol.HTTP_1_1, httpStatus);
    }

    public HttpResponseLine(HttpProtocol httpProtocol, HttpStatus httpStatus) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
    }

    public String combine() {
        return String.format(COMBINE_FORMAT, httpProtocol.getProtocol(), httpStatus.getResponseLine());
    }
}
