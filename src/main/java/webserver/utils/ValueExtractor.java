package webserver.utils;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValueExtractor {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int KEY_VALUE_LENGTH = 2;
    private static final String DEFAULT_VALUE = "";
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private ValueExtractor() {
    }

    public static Map<String, List<String>> extract(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return new HashMap<>();
        }

        return Arrays.stream(value.split(PARAM_DELIMITER))
            .map(param -> param.split(KEY_VALUE_DELIMITER))
            .collect(groupingBy(param -> param[KEY_INDEX], mapping(ValueExtractor::extractValue, toList())));
    }

    private static String extractValue(String[] param) {
        if (param.length < KEY_VALUE_LENGTH) {
            return DEFAULT_VALUE;
        }
        return param[VALUE_INDEX];
    }
}
