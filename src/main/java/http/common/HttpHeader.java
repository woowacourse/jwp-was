package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static http.common.HttpSessionManager.JSESSIONID;

public class HttpHeader {

    private static final String COOKIE = "Cookie";
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_DELIMITER = ",";
    private static final String HEADER_DELIMITER = ": ";
    private static final String NEW_LINE = "\r\n";
    private Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public HttpHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void putHeader(final String name, final String value) {
        headers.put(name, value);
    }

    public String getContentType() {
        return headers.get(ACCEPT).split(ACCEPT_DELIMITER)[0];
    }

    public Cookies getCookies() {
        return new Cookies(headers.get(COOKIE));
    }

    public HttpSession getSession() {
        return HttpSessionManager.getSession(getCookies().getCookie(JSESSIONID));
    }

    public Set<Entry<String,String>> entrySet() {
        return headers.entrySet();
    }
}
