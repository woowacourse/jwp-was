package utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    private static final String PARAM_BUNDLE_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static Map<String, String> extractParams(String paramBundle) {
        Map<String, String> output = new HashMap<>();
        String[] params = paramBundle.split(PARAM_BUNDLE_DELIMITER);
        for (String param : params) {
            String[] keyValues = param.split(KEY_VALUE_DELIMITER);
            output.put(keyValues[0], keyValues[1]);
        }
        return output;
    }
}
