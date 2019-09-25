package utils.parser;

import java.util.Map;

/**
 * WooWaMarkupLanguage
 * URI ## controller
 */
public class WWMLParser extends KeyValueParser {
    private static final String PAIR_DELIMITER = "(\\r\\n|\\r|\\n)+";
    private static final String KEY_VALUE_DELIMITER = "##";

    public static Map<String, String> toMap(String input) {
        return toMap(PAIR_DELIMITER, KEY_VALUE_DELIMITER, input);
    }
}