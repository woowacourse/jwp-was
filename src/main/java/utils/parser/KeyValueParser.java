package utils.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyValueParser {
    private static final Logger logger = LoggerFactory.getLogger(KeyValueParser.class);

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int KEY_VALUE_SIZE = 2;

    public static String debugString(Map<String, String> x) {
        final StringBuilder acc = new StringBuilder();
        x.forEach((key, value) -> acc.append(String.format("%s: %s\r\n", key, value)));
        return acc.toString();
    }

    protected static Map<String, String> toMap(String pairDelimiter, String keyValueDelimiter, String input) {
        return Stream.of(input.split(pairDelimiter))
                .map(keyValueLine -> keyValueLine.split(keyValueDelimiter))
                .filter(keyValueArray -> keyValueArray.length == KEY_VALUE_SIZE)
                .map(pair -> new String[]{pair[KEY_INDEX].trim(), pair[VALUE_INDEX].trim()})
                .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX]));
    }

    static Map<String, String> toMapByDecode(String pairDelimiter, String keyValueDelimiter, String input) {
        try {
            input = URLDecoder.decode(input, StandardCharsets.UTF_8.name());
            return toMap(pairDelimiter, keyValueDelimiter, input);
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
            return new HashMap<>();
        }
    }

    public static String line(String key, String value) {
        return key + "=" + value;
    }
}