package utils;

import java.util.HashMap;
import java.util.Map;

public class ParamUtils {
    private static final String DELIMITER_OF_PARAM_DATA = "&";
    private static final String DELIMITER_OF_KEY_VALUE = "=";

    public static Map<String, String> createParams(String parameter) {
        Map<String, String> map = new HashMap<>();
        if (parameter.isEmpty()) {
            return map;
        }
        String[] data = parameter.split(DELIMITER_OF_PARAM_DATA);
        for (String datum : data) {
            map.put(datum.split(DELIMITER_OF_KEY_VALUE)[0], datum.split(DELIMITER_OF_KEY_VALUE)[1]);
        }
        return map;
    }
}
