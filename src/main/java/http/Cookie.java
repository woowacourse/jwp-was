package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookie {
    private static final String EQUAL_SIGN = "=";
    private static final String SEMI_COLON = ";";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String LOGINED = "logined";
    private static final int NAME_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private Map<String, String> cookies = new HashMap<>();

    public Cookie() {
    }

    public void parse(String line) {
        String[] tokens = line.split(SEMI_COLON);
        for (String token : tokens) {
            String[] subTokens = token.trim().split(EQUAL_SIGN);
            cookies.put(subTokens[NAME_INDEX], subTokens[VALUE_INDEX]);
        }
    }

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public String build() {
        List<String> lines = new ArrayList<>();
        for (String key : cookies.keySet()) {
            String line = key +
                    EQUAL_SIGN +
                    cookies.get(key);
            lines.add(line);
        }
        return String.join("; ", lines);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public String getCookieValue(String name) {
        return cookies.get(name);
    }
}
