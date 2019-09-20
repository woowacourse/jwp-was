package utils.parser;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleStringParser implements KeyValueParser {
    private final String pairDelimiter;
    private final String keyValueDelimiter;

    protected SimpleStringParser(String pairDelimiter, String keyValueDelimiter) {
        this.pairDelimiter = pairDelimiter;
        this.keyValueDelimiter = keyValueDelimiter;
    }

    @Override
    public Map<String, String> toMap(String input) {
        return Stream.of(input.split(this.pairDelimiter))
                    .map(x -> x.split(this.keyValueDelimiter))
                    .filter(x -> x.length == 2)
                    .map(pair -> new String[] { pair[0].trim(), pair[1].trim() })
                    .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }
}