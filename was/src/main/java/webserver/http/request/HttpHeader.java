package webserver.http.request;

import static webserver.http.HttpHeaderFields.*;

import java.util.HashMap;
import java.util.Map;

import webserver.http.session.HttpSession;

public class HttpHeader {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String COOKIE_DELIMITER = "=";
    private static final String SEMI_COLON = ";";

    private final Map<String, String> fields;

    public HttpHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public static HttpHeader withSession(HttpSession session) {
        Map<String, String> fields = new HashMap<>();
        fields.put(SET_COOKIE, JSESSIONID + COOKIE_DELIMITER + session.getId() + SEMI_COLON);
        return new HttpHeader(fields);
    }

    public String get(String key) {
        return fields.get(key);
    }

    public void put(String key, String value) {
        fields.put(key, value);
    }

    public boolean hasSession() {
        if (!fields.containsKey(COOKIE)) {
            return false;
        }
        return fields.get(COOKIE).contains(JSESSIONID);
    }

    public boolean containsKey(String key) {
        return fields.containsKey(key);
    }
}
