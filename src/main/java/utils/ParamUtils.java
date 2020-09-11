package utils;

import java.util.Map;

public class ParamUtils {
    private static final String DELIMITER_OF_PARAM_DATA = "&";
    private static final String DELIMITER_OF_KEY_VALUE = "=";

    public static void putParameter(Map<String, String> params, String body) {
        String[] data = body.split(DELIMITER_OF_PARAM_DATA);
        for (String datum : data) {
            params.put(datum.split(DELIMITER_OF_KEY_VALUE)[0], datum.split(DELIMITER_OF_KEY_VALUE)[1]);
        }
    }
}
