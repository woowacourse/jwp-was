package dev.luffy.http.request;

import java.util.Objects;

import dev.luffy.http.excption.NotFoundExtensionException;

public class HttpRequestUrl {

    private static final String QUERY_STRING_DELIMITER = "?";
    public static final String EMPTY_STRING = "";
    public static final String EXTENSION_DELIMITER = ".";
    public static final String NOT_FOUND_EXTENSION_MESSAGE = "확장자가 없습니다.";

    private String url;

    private HttpRequestUrl(String url) {
        this.url = url;
    }

    public static HttpRequestUrl of(String url) {
        return new HttpRequestUrl(url);
    }

    public String getPath() {
        if (!hasParams()) {
            return url;
        }
        return url.substring(0, url.indexOf(QUERY_STRING_DELIMITER));
    }

    public String getParams() {
        if (!hasParams()) {
            return "";
        }
        return url.substring(url.indexOf(QUERY_STRING_DELIMITER) + 1);
    }

    public boolean hasParams() {
        return url.contains(QUERY_STRING_DELIMITER);
    }

    public boolean isEmptyParams() {
        return EMPTY_STRING.equals(getParams());
    }

    public boolean hasExtension() {
        return isPathContains(EXTENSION_DELIMITER);
    }

    private boolean isPathContains(String contains) {
        return getPath().contains(contains);
    }

    public String getExtension() {
        String path = getPath();
        checkExtension(path);
        return path.substring(path.lastIndexOf(EXTENSION_DELIMITER));
    }

    private void checkExtension(String path) {
        if (!path.contains(EXTENSION_DELIMITER)) {
            throw new NotFoundExtensionException(NOT_FOUND_EXTENSION_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestUrl that = (HttpRequestUrl) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "HttpRequestUrl{" +
                "url='" + url + '\'' +
                '}';
    }
}
