package http;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestUri {
    public static final String URL_PARAMETER_DELIMITER = "\\?";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    private String path;
    private Map<String, String> queryParams;

    public RequestUri(String requestUri) {
        this.path = parsePath(requestUri);
        this.queryParams = parseQueryParams(requestUri);
    }

    private String parsePath(String requestUri) {
        return requestUri.split(URL_PARAMETER_DELIMITER)[0];
    }

    private Map<String, String> parseQueryParams(String requestUri) {
        if (requestUri.split(URL_PARAMETER_DELIMITER).length == 1) {
            return null;
        }

        String queryString = requestUri.split(URL_PARAMETER_DELIMITER)[1];
        String[] paramPairs = queryString.split(PARAMETER_DELIMITER);

        return Arrays.stream(paramPairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));
    }

    public boolean isStaticFile() {
        return ResourceType.isStaticFile(this.path);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
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
