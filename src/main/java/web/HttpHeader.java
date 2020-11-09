package web;

import web.cookie.CookieOption;
import web.cookie.HttpCookies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static web.cookie.HttpCookie.COOKIE;

public class HttpHeader {
    private static final String TOKEN_DELIMITER = ": ";
    private static final String NEW_LINE = System.lineSeparator();

    private final Map<String, String> headers = new HashMap<>();
    private final HttpCookies httpCookies;

    public static HttpHeader ofResponse() {
        return new HttpHeader();
    }

    public static HttpHeader ofRequest(List<String> lines) {
        return new HttpHeader(lines);
    }

    private HttpHeader() {
        httpCookies = new HttpCookies();
    }

    private HttpHeader(List<String> lines) {
        for (String line : lines) {
            String[] tokens = line.split(TOKEN_DELIMITER);
            headers.put(tokens[0], tokens[1]);
        }
        httpCookies = new HttpCookies(headers.get(COOKIE));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        String contentLength = headers.get(HeaderName.CONTENT_LENGTH.getName());
        return Integer.parseInt(contentLength);
    }

    public void put(String key, String value) {
        this.headers.put(key, value);
    }

    public String get(String key) {
        return this.getHeaders().get(key);
    }

    public String write() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(TOKEN_DELIMITER)
                    .append(entry.getValue())
                    .append(NEW_LINE);
        }
        stringBuilder.append(httpCookies.convertToResponse())
                .append(NEW_LINE);
        return stringBuilder.toString();
    }

    public void addCookie(String key, String value) {
        this.httpCookies.add(key, value);
    }

    public void addCookie(String key, String value, CookieOption cookieOption) {
        this.httpCookies.add(key, value, cookieOption);
    }

    public void addCookie(String key, String value, CookieOption cookieOption, String optionValue) {
        this.httpCookies.add(key, value, cookieOption, optionValue);
    }

    public HttpCookies getCookies() {
        return this.httpCookies;
    }
}
