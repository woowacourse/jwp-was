package webserver.request;

public class RequestLine {
    private static final String QUERY_STRING_DELIMITER = "?";

    private final HttpMethod httpMethod;
    private final String path;
    private final String data;

    private RequestLine(HttpMethod httpMethod, String path, String data) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.data = data;
    }

    public static RequestLine of(HttpMethod httpMethod, String resource) {
        if (resource.contains(QUERY_STRING_DELIMITER)) {
            int startIndex = resource.indexOf(QUERY_STRING_DELIMITER);
            String path = resource.substring(0, startIndex);
            String data = resource.substring(startIndex + 1);
            return new RequestLine(httpMethod, path, data);
        }
        return new RequestLine(httpMethod, resource, null);
    }

    public boolean isMatchHttpMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public boolean containsPath(String path) {
        return this.path.contains(path);
    }

    public boolean isNotExistData() {
        return this.data == null;
    }

    public String getPath() {
        return path;
    }

    public String getData() {
        return data;
    }

    public String getMethod() {
        return this.httpMethod.getMethodName();
    }
}
