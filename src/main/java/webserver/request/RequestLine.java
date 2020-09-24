package webserver.request;

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
        validateQueryString(startIndex);
        return resource.substring(startIndex + 1);
    }

    private void validateQueryString(int startIndex) {
        if (startIndex < MIN_INDEX) {
            throw new RuntimeException("QueryString을 포함하고 있지 않습니다.");
        }
    }

    public boolean isMatchHttpMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public boolean containsPath(String path) {
        return resource.contains(path);
    }

    public String getResource() {
        return resource;
    }
}
