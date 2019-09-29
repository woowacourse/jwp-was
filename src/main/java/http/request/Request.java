package http.request;

import exception.NotFoundRequestElementException;
import http.Cookie;
import http.Session;
import http.SessionStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Request {
    private static final String METHOD = "Method";
    private static final String BODY_PARAMETER_SEPARATOR = "?";
    private static final String HEADER_SEPARATOR = " ";
    private static final String COOKIE = "Cookie";
    private static final String SEMI_REGEX = ";";
    private static final String SEPARATOR = "=";
    private static final String JSESSION = "JSESSION";
    private static final int METHOD_FIRST = 0;
    private static final int PATH = 1;

    private Map<String, String> header;
    private Map<String, String> parameter;
    private Map<String, Cookie> cookies;
    private Session session;

    public Request(Map<String, String> header, Map<String, String> parameter) {
        this.header = header;
        this.parameter = parameter;
        this.cookies = new HashMap<>();
        containsCookie(header);
    }

    public String getMethod() {
        return header.get(METHOD).split(HEADER_SEPARATOR)[METHOD_FIRST];
    }

    public String getPath() {
        String path = header.get(METHOD).split(HEADER_SEPARATOR)[PATH];

        if (path.contains(BODY_PARAMETER_SEPARATOR)) {
            return path.substring(0, path.lastIndexOf(BODY_PARAMETER_SEPARATOR));
        }

        return path;
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }

    public void setSession() {
        this.session = SessionStore.getSession(getCookie(JSESSION).getValue());
    }

    public boolean mismatchSessionId() {
        return !cookies.get(JSESSION).getValue().equals(session.getId());
    }

    public boolean notContainSession() {
        return getCookie(JSESSION) == null;
    }

    public void setSessionValue(String name, Object value) {
        session.setAttribute(name, value);
    }

    public String getHeader(String key) {
        String element = header.get(key);

        if (Objects.isNull(element)) {
            throw new NotFoundRequestElementException();
        }
        return element;
    }

    public String getSessionId() {
        return session.getId();
    }

    protected Cookie getCookie(String key) {
        return cookies.get(key);
    }

    private void containsCookie(Map<String, String> header) {
        if (header.containsKey(COOKIE)) {
            String[] cookies = header.get(COOKIE).split(SEMI_REGEX);
            addCookie(cookies);
        }
    }

    private void addCookie(String[] cookies) {
        for (String value : cookies) {
            String[] cookieInfo = value.trim().split(SEPARATOR);
            Cookie cookie = new Cookie(cookieInfo[0], cookieInfo[1]);
            this.cookies.put(cookie.getName(), cookie);
        }
    }
}
