package webserver.http.response;

public class HttpStatusLine {
    private static final String DEFAULT_VERSION = "HTTP/1.1";

    public static String convertToString(HttpStatus httpStatus) {
        return DEFAULT_VERSION + " " + httpStatus.convertToString();
    }
}
