package utils;

import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
    public static String extractWholeUrl(String request) {
        return request.split(" ")[1];
    }

    public static String extractPath(String wholeUrl) {
        return wholeUrl.split("\\?")[0];
    }

    public static Map<String, String> extractParams(String wholeUrl) {
        Map<String, String> output = new HashMap<>();
        String paramBundle = wholeUrl.split("\\?")[1];
        String[] params = paramBundle.split("&");
        for (String param : params) {
            String[] keyValues = param.split("=");
            output.put(keyValues[0], keyValues[1]);
        }
        return output;
    }
}
