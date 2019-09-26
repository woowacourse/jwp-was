package webserver.http.httpRequest;

import webserver.http.cookie.Cookies;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpRequest.CONTENT_LENGTH;
import static webserver.http.HttpRequest.JSESSION_ID;

public class HttpRequestHeader {
    private static final String HEADER_LINE_SEPARATOR = "\n";
    private static final String HEADER_SEPARATOR = ": ";

    private final Map<String, String> headers;

    private final Cookies cookies;

    private HttpRequestHeader(Map<String, String> headers, Cookies cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public static HttpRequestHeader of(String header) {
        HashMap<String, String> headers = new HashMap<>();
        Cookies cookies = new Cookies();
        parseBody(header, headers, cookies);

        return new HttpRequestHeader(headers, cookies);
    }

    private static void parseBody(String header, HashMap<String, String> headers, Cookies cookies) {
        String[] headerLines = header.split(HEADER_LINE_SEPARATOR);
        for (String headerLine : headerLines) {
            String[] headerLinePair = headerLine.split(HEADER_SEPARATOR);
            if (headerLinePair[0].equals("Cookie")) {
                String[] inputCookies = headerLinePair[1].split("=");
                String cookieKey = inputCookies[0];
                String cookieValue = inputCookies[1];
                cookies.addCookie(cookieKey, cookieValue);
            } else {
                headers.put(headerLinePair[0], headerLinePair[1]);
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
        return headers.get("Cookie");
    }

    public String getSessionId() {
        return cookies.getCookieBy(JSESSION_ID);
    }
}
