package kr.wootecat.dongle.http.response;

import static kr.wootecat.dongle.http.HttpStatus.*;

import kr.wootecat.dongle.http.HttpStatus;

public class HttpStatusLine {

    private static final String DEFAULT_VERSION = "HTTP/1.1";

    private final String version;
    private HttpStatus httpStatus;

    private HttpStatusLine(String version, HttpStatus httpStatus) {
        this.version = version;
        this.httpStatus = httpStatus;
    }

    public static HttpStatusLine withDefaultVersion() {
        return new HttpStatusLine(DEFAULT_VERSION, OK);
    }

    public String getVersion() {
        return version;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void changeHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
