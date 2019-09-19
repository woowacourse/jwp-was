package utils.parser;

public class KeyValueParserFactory {
    private static final KeyValueParserFactory instance = new KeyValueParserFactory();
    private static final KeyValueParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final KeyValueParser queryStringParser = new QueryStringParser();
    private static final KeyValueParser contentTypeParser = new ContentTypeParser();

    public static KeyValueParserFactory getInstance() {
        return instance;
    }

    public KeyValueParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public KeyValueParser queryStringParser() {
        return queryStringParser;
    }

    public KeyValueParser contentTypeParser() {
        return contentTypeParser;
    }

    private KeyValueParserFactory() {}
}