package utils.parser;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleStringParser implements KeyValueParser<Map<String, String>> {
    private final String pairDelimiter;
    private final String keyValueDelimiter;

    protected SimpleStringParser(String pairDelimiter, String keyValueDelimiter) {
        this.pairDelimiter = pairDelimiter;
        this.keyValueDelimiter = keyValueDelimiter;
    }

    @Override
    public Map<String, String> interpret(String input) {
        return Stream.of(input.split(this.pairDelimiter))
                    .filter(x -> x.contains(this.keyValueDelimiter))
                    .map(x -> {
                        final String[] pair = x.split(this.keyValueDelimiter, 2);
                        return new String[] { pair[0].trim(), pair[1].trim() };
                    })
                    .filter(pair -> !pair[0].isEmpty())
                    .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }
}