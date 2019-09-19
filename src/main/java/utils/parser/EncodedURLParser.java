package utils.parser;

import java.net.URLDecoder;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncodedURLParser implements KeyValueParser {
    private final String pairDelimiter;
    private final String keyValueDelimiter;

    protected EncodedURLParser(String pairDelimiter, String keyValueDelimiter) {
        this.pairDelimiter = pairDelimiter;
        this.keyValueDelimiter = keyValueDelimiter;
    }

    @Override
    public Map<String, String> toMap(String chunk) {
        return Stream.of(chunk.split(pairDelimiter))
                    .map(x -> x.split(keyValueDelimiter))
                    .filter(x -> x.length == 2)
                    .map(pair -> new String[] {
                            URLDecoder.decode(pair[0].trim()),
                            URLDecoder.decode(pair[1].trim())
                    })
                    .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }
}