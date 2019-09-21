package http;

import java.util.Map;
import java.util.Objects;

public class HttpUri {

    private String path;
    private boolean hasQueryString;
    private Map<String, String> queryParams;

    public HttpUri(final String path, final boolean hasQueryString, Map<String, String> queryParams) {
        this.path = path;
        this.hasQueryString = hasQueryString;
        this.queryParams = queryParams;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpUri httpUri = (HttpUri) o;
        return hasQueryString == httpUri.hasQueryString &&
            path.equals(httpUri.path) &&
            queryParams.equals(httpUri.queryParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, hasQueryString, queryParams);
    }
}
