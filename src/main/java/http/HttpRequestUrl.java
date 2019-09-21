package http;

import java.util.Objects;

import http.excption.NoQueryParamsException;

public class HttpRequestUrl {

    private String url;

    private HttpRequestUrl(String url) {
        this.url = url;
    }

    public static HttpRequestUrl of(String url) {
        return new HttpRequestUrl(url);
    }

    public String getPath() {
        if (!url.contains("?")) {
            return url;
        }
        return url.substring(0, url.indexOf("?"));
    }

    public String getParams() {
        checkQueryParamExist();
        return url.substring(url.indexOf("?") + 1);
    }


    private void checkQueryParamExist() {
        if (!url.contains("?")) {
            throw new NoQueryParamsException("쿼리 문자열이 존재하지 않습니다.");
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

    public boolean hasParams() {
        try {
            checkQueryParamExist();
            return true;
        } catch (NoQueryParamsException e) {
            return false;
        }
    }
}
