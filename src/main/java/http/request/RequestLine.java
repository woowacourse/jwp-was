package http.request;

public class RequestLine {
    private static final String DELIMITER_OF_REQUEST_LINE = " ";

    private final String method;
    private final String url;
    private final String version;

    public RequestLine(String requestLine) {
        String[] splitedRequestLine = requestLine.split(DELIMITER_OF_REQUEST_LINE);
        method = splitedRequestLine[0];
        url = splitedRequestLine[1];
        version = splitedRequestLine[2];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
