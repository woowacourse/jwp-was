package utils.parser;

public class QueryStringParser extends URLParser {
    protected QueryStringParser() {
        super("&", "=");
    }
}