package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    public static final String WHOLE_URL_DELIMITER = " ";
    public static final String PATH_PARAM_DELIMITER = "\\?";
    public static final String PARAM_BUNDLE_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    public static String extractWholeUrl(String request) {
        String wholeUrl = request.split(WHOLE_URL_DELIMITER)[1];
        try {
            return URLDecoder.decode(wholeUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public static String extractPath(String wholeUrl) {
        return wholeUrl.split(PATH_PARAM_DELIMITER)[0];
    }

    public static Map<String, String> extractParams(String wholeUrl) {
        validateParams(wholeUrl);

        Map<String, String> output = new HashMap<>();
        String paramBundle = wholeUrl.split(PATH_PARAM_DELIMITER)[1];
        String[] params = paramBundle.split(PARAM_BUNDLE_DELIMITER);
        for (String param : params) {
            String[] keyValues = param.split(KEY_VALUE_DELIMITER);
            output.put(keyValues[0], keyValues[1]);
        }
        return output;
    }

    private static void validateParams(String wholeUrl) {
        if (wholeUrl.split(PATH_PARAM_DELIMITER).length != 2) {
            throw new IllegalArgumentException("쿼리 파라미터가 존재하지 않습니다");
        }
    }
}
