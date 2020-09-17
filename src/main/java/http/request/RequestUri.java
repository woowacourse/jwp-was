package http.request;

import java.util.Objects;

public class RequestUri {
    private static final String DELIMITER = "\\?";

    private final String path;
    private final QueryParameters queryParameters;

    private RequestUri(String path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    private RequestUri(String path) {
        this(path, null);
    }

    public static RequestUri from(String requestUri) {
        Objects.requireNonNull(requestUri);
        if (hasQueryParameters(requestUri)) {
            return new RequestUri(requestUri);
        }
        String[] tokens = requestUri.split(DELIMITER);
        return new RequestUri(tokens[0], QueryParameters.from(tokens[1]));
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
