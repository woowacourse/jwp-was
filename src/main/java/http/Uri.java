package http;

import java.util.Objects;

public class Uri {
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int QUERY_PARAMETER_CONTAINS_URI_LENGTH = 2;

    private final String path;
    private final QueryParameters queryParameters;

    public Uri(final String path, final QueryParameters queryParameters) {
        this.path = Objects.requireNonNull(path, "uri path가 존재하지 않습니다.");
        this.queryParameters = queryParameters;
    }

    public static Uri from(final String uri) {
        String[] splitUri = uri.split(QUERY_STRING_DELIMITER);
        if (splitUri.length == QUERY_PARAMETER_CONTAINS_URI_LENGTH) {
            return new Uri(splitUri[0], QueryParameters.from(splitUri[1]));
        }
        return new Uri(splitUri[0], null);
    }

    public boolean endsWith(final String extension) {
        return path.endsWith(extension);
    }

    public boolean matchesPath(final String path) {
        return getPath().equals(path);
    }

    public String getPath() {
        return path;
    }

    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

    public String getParameter(final String key) {
        return queryParameters.getParameter(key);
    }

    @Override
    public String toString() {
        return "Uri{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                '}';
    }
}
