package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String NAME_VALUE_DELIMITER = "=";
    private static final int VALUE_INDEX = 1;
    private static final int NAME_INDEX = 0;

    public static Map<String, String> extractQueryString(String queryString) {
        return Arrays.stream(queryString.split(PARAMETER_DELIMITER))
                .map(token -> token.split(NAME_VALUE_DELIMITER))
                .collect(Collectors.toMap(
                        nameAndValue -> nameAndValue[NAME_INDEX],
                        nameAndValue -> nameAndValue[VALUE_INDEX],
                        (a, b) -> b));
    }
}
