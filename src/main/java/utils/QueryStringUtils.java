package utils;

import java.util.HashMap;
import java.util.Map;

public class QueryStringUtils {

    private static final String DELIMITER_BETWEEN_VARIABLE = "&";
    private static final String DELIMIITER_BETWEEN_KEY_VALUE = "=";

    public static Map<String, String> parse(String query) {
        Map<String, String> info = new HashMap<>();
        String[] tokens = query.split(DELIMITER_BETWEEN_VARIABLE);

        for (String token : tokens) {
            String[] keyValue = token.split(DELIMIITER_BETWEEN_KEY_VALUE);
            info.put(keyValue[0], keyValue[1]);
        }

        return info;
    }
}
