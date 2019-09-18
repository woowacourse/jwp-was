package utils.parser;

public class KeyValueParserFactory {
    private static final KeyValueParserFactory instance = new KeyValueParserFactory();
    private static final StringChunkParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final StringChunkParser queryStringParser = new QueryStringParser();

    public static KeyValueParserFactory getInstance() {
        return instance;
    }

    public StringChunkParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public StringChunkParser queryStringParser() {
        return queryStringParser;
    }

    private KeyValueParserFactory() {}
}