package kr.wootecat.dongle.model.http.response;

import kr.wootecat.dongle.model.http.HttpStatus;

public class HttpStatusLine {

    private static final String DEFAULT_VERSION = "HTTP/1.1";

    private final String version;
    private HttpStatus httpStatus;

    private HttpStatusLine(String version, HttpStatus httpStatus) {
        this.version = version;
        this.httpStatus = httpStatus;
    }

    public static HttpStatusLine withDefaultVersion(HttpStatus httpStatus) {
        return new HttpStatusLine(DEFAULT_VERSION, httpStatus);
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
