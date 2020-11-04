package web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import web.session.HttpSession;
import web.session.SessionStorage;

public class HttpRequestHeaders {
    private static final String COOKIE = "Cookie";
    private static final String COLON = ": ";

    private Map<String, String> params;

    private HttpRequestHeaders(Map<String, String> params) {
        this.params = params;
    }

    public static HttpRequestHeaders from(BufferedReader br) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            String[] values = line.split(COLON);
            headerMap.put(values[0], values[1]);
            line = br.readLine();
        }
        return new HttpRequestHeaders(headerMap);
    }

    public int getContentLength() {
        String length = getValue("Content-Length");
        return Objects.isNull(length) ? 0 : Integer.parseInt(length);
    }

    public String getValue(String key) {
        return this.params.get(key);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public HttpCookie getCookies() {
        return new HttpCookie(getValue(COOKIE));
    }

    public HttpSession getSession() {
        return SessionStorage.getSession(getCookies().getCookie(HttpSession.SESSION_ID));
    }
}
