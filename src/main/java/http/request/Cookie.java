package http.request;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String rawCookie) {
        String[] tokens = rawCookie.split("; ");
        for (String token: tokens) {
            String[] tmp = token.split("=");
            cookies.put(tmp[0], tmp[1]);
        }
    }
}
