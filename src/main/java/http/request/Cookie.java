package http.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookie {
    public static final String EQUAL_SIGN = "=";
    private static final String SEMI_COLON = ";";
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

    public boolean isLogined() {
        return cookies.containsKey("JSESSIONID");
    }

    public String build() {
        List<String> lines = new ArrayList<>();
        for (String key : cookies.keySet()) {
            StringBuilder stringBuilder = new StringBuilder()
                    .append(key)
                    .append(EQUAL_SIGN)
                    .append(cookies.get(key));
            String line = stringBuilder.toString();
            lines.add(line);
        }
        return String.join("; ", lines);
    }
}
