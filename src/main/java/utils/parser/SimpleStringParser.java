package utils.parser;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleStringParser implements KeyValueParser {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int KEY_VALUE_SIZE = 2;

    private final String pairDelimiter;
    private final String keyValueDelimiter;

    protected SimpleStringParser(String pairDelimiter, String keyValueDelimiter) {
        this.pairDelimiter = pairDelimiter;
        this.keyValueDelimiter = keyValueDelimiter;
    }

    @Override
    public Map<String, String> toMap(String input) {
        return Stream.of(input.split(this.pairDelimiter))
                    .map(keyValueLine -> keyValueLine.split(this.keyValueDelimiter))
                    .filter(keyValueArray -> keyValueArray.length == KEY_VALUE_SIZE)
                    .map(pair -> new String[] { pair[KEY_INDEX].trim(), pair[VALUE_INDEX].trim() })
                    .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX]));
    }
}