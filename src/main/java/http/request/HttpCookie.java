package http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    Map<String, String> cookies = new HashMap<>();

    public HttpCookie(final String cookies) {
        Arrays.stream(cookies.split(";"))
                .map(line -> line.trim())
                .map(line -> Arrays.asList(line.split("=")))
                .forEach(line -> this.cookies.put(line.get(0), line.get(1)));
    }

    public String getValue(String key) {
        return cookies.get(key);
    }
}
