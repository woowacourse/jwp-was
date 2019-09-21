package utils.parser;

import java.net.URLDecoder;
import java.util.Map;

public class URLParser extends SimpleStringParser {
    protected URLParser(String pairDelimiter, String keyValueDelimiter) {
        super(pairDelimiter, keyValueDelimiter);
    }

    @Override
    public Map<String, String> interpret(String input) {
        return super.interpret(URLDecoder.decode(input));
    }
}