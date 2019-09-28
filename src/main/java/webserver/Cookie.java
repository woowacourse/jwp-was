package webserver;

import java.util.HashMap;
import java.util.Map;

public class Cookie {

    public static final String END_POINT = "endpoint";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String LOGINED = "logined";
    private static final String EQUAL_SIGN = "=";
    private static final String FIELD_SEPARATOR = ";";
    private static final String PATH = "Path";
    private final Map<String, String> cookies = new HashMap<>();

    public void put(String key, String value) {
        cookies.put(key, value);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public static String createLoginState(boolean isSuccessful) {
        return LOGINED + EQUAL_SIGN + isSuccessful;
    }

    public static String createJSessionIdState(String sessionId, String path) {
        return new StringBuilder().append(JSESSIONID)
                .append(EQUAL_SIGN)
                .append(sessionId)
                .append(FIELD_SEPARATOR)
                .append(PATH)
                .append(EQUAL_SIGN)
                .append(path)
                .toString();
    }
}
