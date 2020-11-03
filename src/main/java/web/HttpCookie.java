package web;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpCookie {

    private static final String SET_COOKIE = "Set-Cookie: ";
    private final Map<String, String> cookies;

    public HttpCookie() {
        this.cookies = new LinkedHashMap<>();
        this.cookies.put("Path", "/");
    }

    public HttpCookie(String cookies) {
        this();
        if (StringUtils.isEmpty(cookies)) {
            return;
        }

        String[] cookieList = cookies.split("; ");

        for (String cookie : cookieList) {
            String[] entry = cookie.split("=");
            this.cookies.put(entry[0], entry[1]);
        }
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String convertToResponse() {
        List<String> cookies = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.cookies.entrySet()) {
            cookies.add(entry.getKey() + "=" + entry.getValue());
        }

        return SET_COOKIE + String.join("; ", cookies);
    }
}
