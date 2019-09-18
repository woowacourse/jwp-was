package utils.parser;

public class QueryStringParser extends StringChunkParser {
    protected QueryStringParser() {
        super("&", "=");
    }
}