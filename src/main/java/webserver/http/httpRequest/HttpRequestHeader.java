package webserver.http.httpRequest;

import webserver.SessionManager;
import webserver.http.cookie.Cookies;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static webserver.http.HttpRequest.CONTENT_LENGTH;
import static webserver.http.HttpRequest.JSESSION_ID;

public class HttpRequestHeader {
    public static final String HEADER_LINE_SEPARATOR = "\n";
    public static final String COOKIE = "Cookie";
    private static final String HEADER_SEPARATOR = ": ";
    private static final String COOKIE_SEPARATOR = ";";
    private static final String COOKIE_PAIR_SEPARATOR = "=";

    private final Map<String, String> headers;
    private final Cookies cookies;

    private HttpRequestHeader(Map<String, String> headers, Cookies cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public static HttpRequestHeader create(String header) {
        HashMap<String, String> headers = new HashMap<>();
        Cookies cookies = new Cookies();
        parseHeader(header, headers, cookies);

        return new HttpRequestHeader(headers, cookies);
    }

    private static void parseHeader(String header, HashMap<String, String> headers, Cookies cookies) {
        String[] headerLines = header.split(HEADER_LINE_SEPARATOR);
        for (String headerLine : headerLines) {
            String[] headerLinePair = headerLine.split(HEADER_SEPARATOR);
            headers.put(headerLinePair[0], headerLinePair[1]);
        }
        parseCookie(headers, cookies);
    }

    private static void parseCookie(HashMap<String, String> headers, Cookies cookies) {
        String headerCookie = headers.get(COOKIE);
        if (Objects.nonNull(headerCookie)) {
            String[] inputCookies = headerCookie.split(COOKIE_SEPARATOR);
            for (String cookiePairs : inputCookies) {
                String[] cookiePair = cookiePairs.split(COOKIE_PAIR_SEPARATOR);
                String key = cookiePair[0];
                String value = cookiePair[1];
                cookies.addCookie(key, value);
            }
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(CONTENT_LENGTH));
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getCookie() {
        return headers.get(COOKIE);
    }

    public String getSessionId() {
        String sessionId = cookies.getCookieBy(JSESSION_ID);

        if (Objects.isNull(sessionId) || Objects.isNull(SessionManager.getSession(sessionId))) {
            sessionId = SessionManager.createSession();
            cookies.addCookie(JSESSION_ID, sessionId);
        }
        return sessionId;
    }

    public boolean hasSession() {
        return Objects.nonNull(cookies.getCookieBy(JSESSION_ID));
    }
}
