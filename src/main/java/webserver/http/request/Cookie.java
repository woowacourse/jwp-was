package webserver.http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    public static final String SESSION_ID_KEY = "JSESSIONID";
    private final String LINE_DELIMITER = ";";
    private final String KEY_VALUE_DELIMITER = "=";
    private Map<String, String> cookies;

    public Cookie(String line) {
        this.cookies = parseLine(line);
    }


    private Map<String, String> parseLine(String line) {
        if (line == null) {
            return this.cookies = Collections.emptyMap();
        }
        return Arrays.stream(line.split(LINE_DELIMITER))
                .map(String::trim)
                .map(s -> s.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(key -> key[0], value -> value[1], (p1, p2) -> p1));
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getSessionId() {
        return cookies.get(SESSION_ID_KEY);
    }
}
