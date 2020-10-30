package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import http.Cookies;

public class HttpResponseHeader {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String COLON = ": ";

    private final Map<String, Object> responseHeader;
    private final Cookies cookies;

    public HttpResponseHeader(final Cookies cookies) throws CloneNotSupportedException {
        this.responseHeader = new HashMap<>();
        this.cookies = (Cookies) cookies.clone();
    }

    public void addResponseHeader(String key, Object value) {
        this.responseHeader.put(key, value);
    }

    public Object getValue(final String key) {
        return this.responseHeader.get(key);
    }

    public void addCookie(final String name, final String value) {
        this.cookies.addCookie(name, value);
    }

    public boolean isEmptyCookie() {
        return this.cookies.isEmpty();
    }

    public String flatCookie() {
        return this.cookies.flat();
    }

    public void write(final DataOutputStream dos) throws IOException {
        for (Map.Entry<String, Object> entry : responseHeader.entrySet()) {
            dos.writeBytes(entry.getKey() + COLON + entry.getValue() + NEW_LINE);
        }
    }
}
