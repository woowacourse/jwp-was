package utils;

import java.util.HashMap;
import java.util.Map;

public class SplitUtils {

    private static final String EMPTY_VALUE = "";
    private static final int KEY_VALUE_PAIR_LENGTH = 2;

    private SplitUtils() {
    }

    public static Map<String, String> splitAndThenCollect(String data, String pairDelimiter, String keyValueDelimiter) {
        Map<String, String> result = new HashMap<>();

        String[] keyValues = data.split(pairDelimiter);
        for (String keyValue : keyValues) {
            String[] split = keyValue.split(keyValueDelimiter);
            String key = split[0];
            String value = split.length == KEY_VALUE_PAIR_LENGTH ? split[1] : EMPTY_VALUE;
            result.put(key, value);
        }
        return result;
    }
}
