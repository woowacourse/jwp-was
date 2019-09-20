package http.common;

import http.common.exception.InvalidURLException;
import utils.StringUtils;

import java.util.Objects;

public class URL {
    private final String path;
    private final String queryString;

    public URL(String path, String queryString) {
        checkValidatePath(path);
        checkEmptyUrl(queryString);
        this.path = path;
        this.queryString = queryString;
    }

    //todo: path 검증 로직 추가해서 생성_실패 테스트 케이스에서 확인
    private void checkValidatePath(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new InvalidURLException();
        }
    }

    public static URL of(String url) {
        checkEmptyUrl(url);
        int index = getIndex(url);
        String path = url.substring(0, index);
        String queryString = getQueryString(url, index + 1);
        return new URL(path, queryString);
    }

    private static void checkEmptyUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new InvalidURLException();
        }
    }

    private static String getQueryString(String url, int index) {
        return url.length() >= index ? url.substring(index) : "";
    }

    private static int getIndex(String url) {
        int index = url.indexOf("?");
        return index > 0 ? index : url.length();
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
