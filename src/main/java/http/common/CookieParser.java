package http.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CookieParser {

    private static final String LAST_IS_SEMICOLON_REGEX = "^[a-zA-Z0-9]+;$";
    private static final String ATTRIBUTE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = " ";
    public static final int START_INDEX = 0;

    public static Cookie parse(String line) {
        String[] cookieTokens = Arrays.stream(line.split(COOKIE_DELIMITER)).map(String::trim)
            .toArray(String[]::new);

        Map<String, String> attributeWithValue = new HashMap<>();
        List<String> attributeWithoutValue = new ArrayList<>();
        Arrays.stream(cookieTokens).forEach(cookieToken -> {
            cookieToken = deleteSemicolon(cookieToken);
            putAttributes(attributeWithValue, attributeWithoutValue, cookieToken);
        });

        return new Cookie(attributeWithValue, attributeWithoutValue);
    }

    private static void putAttributes(final Map<String, String> attributeWithValue, final List<String> attributeWithoutValue, final String cookieToken) {
        if (cookieToken.contains(ATTRIBUTE_DELIMITER)) {
            String[] attributeTokens = cookieToken.split(ATTRIBUTE_DELIMITER);
            attributeWithValue.put(attributeTokens[0], attributeTokens[1]);
        } else {
            attributeWithoutValue.add(cookieToken);
        }
    }

    private static String deleteSemicolon(String cookieToken) {
        if (cookieToken.matches(LAST_IS_SEMICOLON_REGEX)) {
            int lastIndex = cookieToken.length() - 1;
            cookieToken = cookieToken.substring(START_INDEX, lastIndex);
        }
        return cookieToken;
    }
}
