package server.web.cookie;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpCookie {
    public static final String COOKIE = "Cookie";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String SET_COOKIE = "Set-Cookie: ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String OPTION_DELIMITER = "; ";
    private final String key;
    private final String value;
    private final Map<CookieOption, String> options;

    public HttpCookie(final String key, final String value) {
        this.key = key;
        this.value = value;
        this.options = new LinkedHashMap<>();
    }

    public void addOption(CookieOption cookieOption) {
        options.put(cookieOption, null);
    }

    public void addOption(CookieOption cookieOption, String value) {
        options.put(cookieOption, value);
    }

    public String write() {
        return SET_COOKIE + key + KEY_VALUE_DELIMITER + value + writeOptions() + NEW_LINE;
    }

    private String writeOptions() {
        StringBuilder optionBuilder = new StringBuilder();
        for (Map.Entry<CookieOption, String> options : options.entrySet()) {
            optionBuilder.append(OPTION_DELIMITER)
                    .append(options.getKey().writeOption(options.getValue()));
        }
        return optionBuilder.toString();
    }

    public boolean hasKey(String key) {
        return this.key.equals(key);
    }

    public String getValue() {
        return value;
    }
}
