package utils;

import static webserver.HttpRequest.*;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    public static Map<String, String> readUrl(String url) {
        String[] urlTokens = url.split("\\?");
        Map<String, String> params = new HashMap<>();
        params.put(PATH, urlTokens[0]);

        if (hasParameters(urlTokens)) {
            String[] paramTokens = urlTokens[1].split("&");

            for (String paramToken : paramTokens) {
                String[] strings = paramToken.split("=");
                params.put(strings[0], strings[1]);
            }
        }

        return params;
    }

    private static boolean hasParameters(String[] urlTokens) {
        return urlTokens.length > 1;
    }
}
