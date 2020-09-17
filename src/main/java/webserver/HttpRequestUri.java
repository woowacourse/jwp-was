package webserver;

import java.util.Objects;

public class HttpRequestUri {
    private static final String DELIMITER = "\\?";

    private final String path;
    private final QueryParameters queryParameters;

    private HttpRequestUri(String path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    private HttpRequestUri(String path) {
        this(path, null);
    }

    public static HttpRequestUri from(String requestUri) {
        Objects.requireNonNull(requestUri);
        if (hasQueryParameters(requestUri)) {
            return new HttpRequestUri(requestUri);
        }
        String[] tokens = requestUri.split(DELIMITER);
        return new HttpRequestUri(tokens[0], QueryParameters.from(tokens[1]));
    }

    private static boolean hasQueryParameters(String requestUri) {
        return requestUri.split(DELIMITER).length == 1;
    }

    public String getParameterBy(String key) {
        return queryParameters.getParameterBy(key);
    }

    public String getPath() {
        return path;
    }
}
