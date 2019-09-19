package utils.parser;

public class KeyValueParserFactory {
    private static final KeyValueParserFactory instance = new KeyValueParserFactory();
    private static final EncodedURLParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final EncodedURLParser queryStringParser = new QueryStringParser();

    public static KeyValueParserFactory getInstance() {
        return instance;
    }

    public EncodedURLParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public EncodedURLParser queryStringParser() {
        return queryStringParser;
    }

    private KeyValueParserFactory() {}
}