package http.request;

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
        if (cookie.contains("; ")) {
            String[] tokens = cookie.split("; ");
            Map<String, String> cookies = new HashMap<>();
            Arrays.stream(tokens)
                    .forEach(s -> cookies.put(s.split("=")[0], s.split("=")[1]));

            return new HttpCookie(cookies);
        }
        return new HttpCookie(Collections.emptyMap());
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }
}
