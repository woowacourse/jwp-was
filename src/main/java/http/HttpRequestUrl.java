package http;

import java.util.Objects;

public class HttpRequestUrl {

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
        return url.substring(0, url.indexOf("?"));
    }

    public String getParams() {
        if (!hasParams()) {
            return "";
        }
        return url.substring(url.indexOf("?") + 1);
    }

    public boolean hasParams() {
        return url.contains("?");
    }

    public boolean isEmptyParams() {
        return "".equals(getParams());
    }

    public boolean hasExtension() {
        return isPathContains(".");
    }

    private boolean isPathContains(String contains) {
        return getPath().contains(contains);
    }

    public String getExtension() {
        String path = getPath();
        if (!path.contains(".")) {
            throw new IllegalArgumentException("확장자가 없습니다.");
        }
        return path.substring(path.lastIndexOf("."));
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
