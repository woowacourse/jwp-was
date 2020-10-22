package http;

import java.util.Map;
import java.util.Objects;

public class RequestUri {
    public static final String URL_PARAMETER_DELIMITER = "\\?";

    private final String path;
    private final QueryParams queryParams;

    public RequestUri(String requestUri) {
        this.path = parsePath(requestUri);
        this.queryParams = new QueryParams(requestUri);
    }

    private String parsePath(String requestUri) {
        return requestUri.split(URL_PARAMETER_DELIMITER)[0];
    }

    public boolean isStaticFile() {
        return ResourceType.isStaticFile(this.path);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParams() {
        return queryParams.getQueryParams();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUri that = (RequestUri) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(queryParams, that.queryParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryParams);
    }
}
