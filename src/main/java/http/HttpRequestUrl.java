package http;

import java.util.HashMap;
import java.util.Map;
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

    public Map<String, String> queryParams() {
        checkQueryParamExist();
        return getQueryParams();
    }

    private void checkQueryParamExist() {
        if (!url.contains("?")) {
            throw new NoQueryParamsException("쿼리 문자열이 존재하지 않습니다.");
        }
    }

    private Map<String, String> getQueryParams() {
        int index = url.indexOf("?");
        Map<String, String> params = new HashMap<>();
        String[] queryParams = url.substring(index + 1).split("&");
        for (String queryParam : queryParams) {
            String[] paramKeyValue = queryParam.split("=");
            params.put(paramKeyValue[0], paramKeyValue[1]);
        }
        return params;
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
}
