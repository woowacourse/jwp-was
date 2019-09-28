package utils.parser;

import java.util.Map;

public class HttpHeaderFieldsParser extends KeyValueParser {
    private static final String PAIR_DELIMITER = "(\\r\\n|\\r|\\n)";
    private static final String KEY_VALUE_DELIMITER = ": ";

    public static Map<String, String> toMap(String input) {
        return toMapByDecode(PAIR_DELIMITER, KEY_VALUE_DELIMITER, input);
    }
}