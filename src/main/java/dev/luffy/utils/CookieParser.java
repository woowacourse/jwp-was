package dev.luffy.utils;

import java.util.HashMap;
import java.util.Map;

public class CookieParser {

    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";

    public static Map<String, String> parse(String sequence) {
        String[] tokens = sequence.split(COOKIE_DELIMITER);
        Map<String, String> cookies = new HashMap<>();
        for (String token : tokens) {
            String[] splitToken = token.split(COOKIE_KEY_VALUE_DELIMITER);
            cookies.put(splitToken[0], splitToken[1]);
        }
        return cookies;
    }
}
