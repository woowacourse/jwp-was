package web;

import web.cookie.CookieOption;
import web.cookie.HttpCookies;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private static final String TOKEN_DELIMITER = ": ";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String COOKIE = "Cookie";

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

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            dataOutputStream.writeBytes(entry.getKey() + TOKEN_DELIMITER + entry.getValue() + NEW_LINE);
        }
        dataOutputStream.writeBytes(httpCookies.convertToResponse() + NEW_LINE);
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
