package utils;

import java.util.HashMap;
import java.util.Map;

public class QueryStringUtils {

    public static Map<String, String> parse(String query) {
        Map<String, String> info = new HashMap<>();
        String[] tokens = query.split("&");

        for (String token : tokens) {
            String[] keyValue = token.split("=");
            info.put(keyValue[0], keyValue[1]);
        }

        return info;
    }
}
