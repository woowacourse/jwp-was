package http.common;

import utils.StringUtils;

import java.util.Objects;

import static utils.StringUtils.BLANK;

public class URL {
    public static final URL EMPTY_URL = new URL(BLANK, BLANK);
    private static final String QUERY_STRING_DELIMITER = "?";
    private final String path;
    private final String queryString;

    private URL(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static URL of(String url) {
        if (StringUtils.isEmpty(url)) {
            return EMPTY_URL;
        }

        int separatorPosition = getSeparatorPosition(url);
        String path = url.substring(0, separatorPosition);
        String queryString = getQueryString(url, separatorPosition + 1);
        return new URL(path, queryString);
    }

    private static String getQueryString(String url, int startIndex) {
        return url.length() >= startIndex ? url.substring(startIndex) : BLANK;
    }

    private static int getSeparatorPosition(String url) {
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
