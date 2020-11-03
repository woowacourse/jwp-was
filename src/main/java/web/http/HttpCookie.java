package web.http;

import java.util.Map;

import utils.HttpRequestUtils;

public class HttpCookie {
    private Map<String, String> cookies;

    public HttpCookie(String cookieValue) {
        cookies = HttpRequestUtils.parseCookies(cookieValue);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }
}
