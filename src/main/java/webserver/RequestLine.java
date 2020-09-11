package webserver;

public class RequestLine {
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final int MIN_INDEX = 0;

    private final HttpMethod httpMethod;
    private final String resource;

    public RequestLine(HttpMethod httpMethod, String resource) {
        this.httpMethod = httpMethod;
        this.resource = resource;
    }

    public String extractQueryString() {
        int startIndex = resource.indexOf(QUERY_STRING_DELIMITER);
        if (startIndex < MIN_INDEX) {
            throw new RuntimeException("QueryString을 포함하고 있지 않습니다.");
        }
        return resource.substring(startIndex + 1);
    }

    public boolean isMatch(RequestLine requestLine) {
        return this.httpMethod == requestLine.httpMethod
                && this.resource.contains(requestLine.getResource());
    }

    public String getResource() {
        return resource;
    }

}
