package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
    public static String extractWholeUrl(String request) {
        String wholeUrl = request.split(" ")[1];
        try {
            return URLDecoder.decode(wholeUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
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
