package http.cookie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.StringUtils.BLANK;

public class Cookies {
    private static final String DELIMITER = "=";
    private static final int NULL_LINE_INDEX = -1;
    private static final int BEGIN_INDEX = 0;
    private final Map<String, String> cookies = new HashMap<>();

    public void addAll(List<String> cookie) {
        if (cookie != null) {
            cookie.forEach(this::addCookie);
        }
    }

    private void addCookie(String cookieEntry) {
        int delimiterIndex = getDelimiterIndex(cookieEntry);
        if (delimiterIndex > BEGIN_INDEX) {
            cookies.put(cookieEntry.substring(BEGIN_INDEX, delimiterIndex),
                    extractCookieValue(cookieEntry, delimiterIndex + 1));
        }
    }

    private int getDelimiterIndex(String line) {
        if (line == null) {
            return NULL_LINE_INDEX;
        }
        line = line.trim();
        return line.indexOf(DELIMITER);
    }

    private String extractCookieValue(String line, int valueStartIndex) {
        return valueStartIndex >= line.length() ? BLANK : line.substring(valueStartIndex);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public void addCookie(Cookie cookie) {
//        cookies.put(cookie.getName(), cookie.getValue());
    }
}
