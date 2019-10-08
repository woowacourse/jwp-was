package utils.parser;

import java.net.URLDecoder;
import java.util.Map;

public final class QueryStringParser extends SimpleStringParser {
    protected QueryStringParser() {
        super("&", "=");
    }

    @Override
    public Map<String, String> interpret(String input) {
        return super.interpret(URLDecoder.decode(input));
    }
}