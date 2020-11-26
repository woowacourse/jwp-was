package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class HttpHeaders {
    private static final HttpHeaders DEFAULT_HEADERS = new HttpHeaders(Collections.unmodifiableMap(new HashMap<>()));
    private static final String HTTP_HEADER_KEY_VALUE_SPLITTER = ": ";
    private static final Integer DEFAULT_INT_VALUE = 0;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE = "Cookie";
    private static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headers;
    private Cookies cookies;

    public static HttpHeaders from(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return DEFAULT_HEADERS;
        }
        Map<String, String> headers = Arrays.stream(input.split(System.lineSeparator()))
            .map(line -> line.split(HTTP_HEADER_KEY_VALUE_SPLITTER))
            .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX]));
        return new HttpHeaders(headers);
    }

    public HttpHeaders() {
        this.headers = new HashMap<>();
        this.cookies = Cookies.empty();
    }

    private HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
        this.cookies = Cookies.of(this.headers.get(COOKIE));
    }

    public HttpSession getSession() {
        return SimpleHttpSession.getHttpSessionStorage(cookies.getSessionId());
    }

    @Nullable
    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        String contentLength = headers.get(CONTENT_LENGTH);

        if (Objects.isNull(contentLength) || contentLength.isEmpty()) {
            return DEFAULT_INT_VALUE;
        }

        return Integer.parseInt(contentLength);
    }

    public int size() {
        return headers.size();
    }

    public void add(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public String getCookie() {
        return headers.get(COOKIE);
    }

    public void setContentType(String contentType) {
        headers.put(CONTENT_TYPE, contentType);
    }

    public void setContentLength(String contentLength) {
        headers.put(CONTENT_LENGTH, contentLength);
    }

    public void setCookie(String cookie) {
        headers.put(SET_COOKIE, cookie);
    }

    public void addCookie(Cookie cookie) {
        cookies.addCookie(cookie);
    }

    public String getHttpHeaderString() {
        String setCookies = cookies.toResponse().stream()
            .map(cookie -> SET_COOKIE + HTTP_HEADER_KEY_VALUE_SPLITTER + cookie)
            .collect(Collectors.joining(System.lineSeparator()));
        return headers.entrySet().stream()
            .map(entry -> entry.getKey() + HTTP_HEADER_KEY_VALUE_SPLITTER + entry.getValue())
            .collect(Collectors.joining(System.lineSeparator()))
            + setCookies;
    }

}
