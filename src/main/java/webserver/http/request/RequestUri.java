package webserver.http.request;

import utils.HttpRequestUtils;

import java.util.Map;
import java.util.Objects;

public class RequestUri {
    private static final int ABS_PATH_INDEX = 0;
    private final String absPathAndQuery;
    private final String absPath;
    private final Map<String, String> queryStrings;

    public RequestUri(String pathAndQuery) {
        absPathAndQuery = pathAndQuery;
        absPath = HttpRequestUtils.parseAbsPathAndQuery(absPathAndQuery)[ABS_PATH_INDEX];
        queryStrings = HttpRequestUtils.parseQueryString(absPathAndQuery);
    }

    public String getAbsPath() {
        return absPath;
    }

    public String getQueryString(String key) {
        return queryStrings.get(key);
    }

    public boolean isSameAbsPath(String targetAbsPath) {
        return absPath.equals(targetAbsPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUri that = (RequestUri) o;
        return Objects.equals(absPathAndQuery, that.absPathAndQuery) &&
                Objects.equals(absPath, that.absPath) &&
                Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(absPathAndQuery, absPath, queryStrings);
    }
}
