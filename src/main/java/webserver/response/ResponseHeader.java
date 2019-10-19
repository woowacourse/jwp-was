package webserver.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static webserver.response.HttpResponse.EQUAL_SIGN;
import static webserver.response.HttpResponse.HEAD_FIELD_SET_COOKIE;

public class ResponseHeader {
    private static final String SESSION_KEY = "JSESSIONID";
    private static final String HEADER_FIELD_SEPARATOR = ": ";
    private static final String COOKIE_SEPARATOR = "; ";

    private Map<String, String> attributes;

    public ResponseHeader() {
        this.attributes = new HashMap<>();
    }

    public boolean addAttribute(String key, String value) {
        if (attributes.containsKey(key)) {
            return false;
        }
        attributes.put(key, value);
        return true;
    }

    public String response() {
        StringBuilder str = new StringBuilder();
        for (String attributeName : attributes.keySet()) {
            str.append(attributeName).append(HEADER_FIELD_SEPARATOR).append(attributes.get(attributeName)).append("\r\n");
        }
        return str.toString();
    }

    public String getSessionId() {
        return getCookies().get(SESSION_KEY);
    }

    private Map<String, String> getCookies() {
        Map<String, String> cookies = new HashMap<>();
        String[] cookieKeyAndValue;
        try {
            cookieKeyAndValue = attributes.get(HEAD_FIELD_SET_COOKIE).split(COOKIE_SEPARATOR);
        } catch (IllegalArgumentException e) {
            return Collections.emptyMap();
        }

        for (String cookie : cookieKeyAndValue) {
            cookies.put(cookie.split(EQUAL_SIGN)[0], cookie.split(EQUAL_SIGN)[1]);
        }
        return cookies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHeader that = (ResponseHeader) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
