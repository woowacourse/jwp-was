package utils.parser;

public class QueryStringParser extends EncodedURLParser {
    protected QueryStringParser() {
        super("&", "=");
    }
}