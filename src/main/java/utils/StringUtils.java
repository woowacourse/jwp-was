package utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    private static final String PARAMETER_DELIMITER = "=";
    private static final String PARAMETERS_DELIMITER = "&";
    private static final int PARAMETER_KEY = 0;
    private static final int PARAMETER_VALUE = 1;

    public static Map<String, String> readParameters(String line) {
        Map<String, String> params = new HashMap<>();
        String[] paramTokens = line.split(PARAMETERS_DELIMITER);

        for (String paramToken : paramTokens) {
            String[] strings = paramToken.split(PARAMETER_DELIMITER);
            params.put(strings[PARAMETER_KEY], strings[PARAMETER_VALUE]);
        }
        return params;
    }
}
