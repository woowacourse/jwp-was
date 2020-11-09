package webserver.http.response;

public class HttpStatusLine {
    private static final String DEFAULT_VERSION = "HTTP/1.1";

    private String version;
    private HttpStatus httpStatus;

    private HttpStatusLine(String version, HttpStatus httpStatus) {
        this.version = version;
        this.httpStatus = httpStatus;
    }

    public static HttpStatusLine withDefaultVersion() {
        return new HttpStatusLine(DEFAULT_VERSION, null);
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
