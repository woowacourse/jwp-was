package http.common;

import http.common.exception.InvalidURLException;
import utils.StringUtils;

import java.util.Objects;

public class URL {
    private static final String QUERY_STRING_DELIMITER = "?";
    private final String path;
    private final String queryString;

    private URL(String path, String queryString) {
        checkValidatePath(path);
        this.path = path;
        this.queryString = queryString;
    }

    private void checkValidatePath(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new InvalidURLException();
        }
    }

    public static URL of(String url) {
        checkEmptyUrl(url);
        int queryStringIndex = getQueryStringIndex(url);
        String path = url.substring(0, queryStringIndex);
        String queryString = getQueryString(url, queryStringIndex + 1);
        return new URL(path, queryString);
    }

    private static void checkEmptyUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new InvalidURLException();
        }
    }

    private static String getQueryString(String url, int index) {
        if (url.length() >= index) {
            return url.substring(index);
        }

        return StringUtils.BLANK;
    }

    private static int getQueryStringIndex(String url) {
        int queryStringIndex = url.indexOf(QUERY_STRING_DELIMITER);
        return queryStringIndex > 0 ? queryStringIndex : url.length();
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return Objects.equals(path, url.path) &&
                Objects.equals(queryString, url.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }
}
