package utils.parser;

public class KeyValueParserFactory {
    private static final KeyValueParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final KeyValueParser queryStringParser = new QueryStringParser();
    private static final KeyValueParser contentTypeParser = new ContentTypeParser();
    private static final KeyValueParser routerParser = new WWMLParser();

    public static KeyValueParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public static KeyValueParser queryStringParser() {
        return queryStringParser;
    }

    public static KeyValueParser contentTypeParser() {
        return contentTypeParser;
    }

    public static KeyValueParser routerParser() {
        return routerParser;
    }
}