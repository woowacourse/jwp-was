package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie of(String cookie) {
        String[] tokens = cookie.split("; ");
        Map<String, String> cookies = new HashMap<>();

        if (tokens.length == 2) {
            Arrays.stream(tokens)
                    .forEach(s -> cookies.put(s.split("=")[0], s.split("=")[1]));
            return new HttpCookie(cookies);
        }
        if (!StringUtils.isBlank(cookie)) {
            cookies.put(cookie.split("=")[0], cookie.split("=")[1]);
            return new HttpCookie(cookies);
        }
        return new HttpCookie(Collections.emptyMap());
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public boolean contains(String key) {
        return cookies.containsKey(key);
    }
}
