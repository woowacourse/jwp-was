package webserver.request;

import utils.HttpRequestUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

public class RequestUri {
    private final URI absPathAndQuery;
    private final String absPath;
    private final Map<String, String> queryStrings;

    public RequestUri(String uri) throws URISyntaxException {
        absPathAndQuery = new URI(uri);
        absPath = absPathAndQuery.getPath();
        queryStrings = HttpRequestUtils.parseQueryString(absPathAndQuery.getQuery());
    }

    public String getAbsPath() {
        return absPath;
    }

    public String getQueryString(String key) {
        return queryStrings.get(key);
    }

    public boolean isFile() {
        return absPath.split("\\.").length >= 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestUri that = (RequestUri) o;

        if (!Objects.equals(absPath, that.absPath)) return false;
        return Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        int result = absPath != null ? absPath.hashCode() : 0;
        result = 31 * result + (queryStrings != null ? queryStrings.hashCode() : 0);
        return result;
    }
}
