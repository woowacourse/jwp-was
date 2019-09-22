package http.request;

public class RequestLine {
    private static final String DELIMITER_OF_REQUEST_LINE = " ";

    private final String method;
    private final String url;
    private final String version;

    public RequestLine(String requestLine) {
        String[] splitedRequestLine = validateRequestLine(requestLine);
        method = splitedRequestLine[0];
        url = splitedRequestLine[1];
        version = splitedRequestLine[2];
    }

    private static String[] validateRequestLine(String requestLine) {
        String[] requestLineData = requestLine.split(DELIMITER_OF_REQUEST_LINE);

        if(requestLineData.length != 3) {
            throw new IllegalArgumentException("Invalid Request Line");
        }

        return requestLineData;
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

    public boolean isGet() {
        return "GET".equals(method);
    }

    public boolean isPost() {
        return "POST".equals(method);
    }
}
