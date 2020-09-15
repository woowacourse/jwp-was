package utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    public static Map<String, String> readParameters(String line) {
        Map<String, String> params = new HashMap<>();
        String[] paramTokens = line.split("&");

        for (String paramToken : paramTokens) {
            String[] strings = paramToken.split("=");
            params.put(strings[0], strings[1]);
        }
        return params;
    }
}
