package http;

import http.exception.InvalidHeaderFormatException;
import jdk.internal.joptsimple.internal.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.net.HttpHeaders.*;

public class HttpHeader {
    public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String HEADER_DELIMITER = ":";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> headers) {
        for (String header : headers) {
            int index = header.indexOf(HEADER_DELIMITER);
            if (index == -1) {
                throw new InvalidHeaderFormatException();
            }
            String key = header.substring(0, index);
            String value = header.substring(index + 1);
            this.headers.put(key.trim(), value.trim());
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(Cookie cookie) {
        headers.put(SET_COOKIE, headers.getOrDefault(SET_COOKIE, Strings.EMPTY) + cookie.convertCookieToString());
    }

    public boolean matchContentType(String contentType) {
        return headers.get(CONTENT_TYPE).equals(contentType);
    }

    public boolean containContentLength() {
        return headers.containsKey(CONTENT_LENGTH);
    }

    public Set<Map.Entry<String, String>> getHeaders() {
        return headers.entrySet();
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, "-1"));
    }
}