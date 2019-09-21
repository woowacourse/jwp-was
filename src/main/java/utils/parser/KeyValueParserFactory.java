package utils.parser;

public class KeyValueParserFactory {
    private static final SimpleStringParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final SimpleStringParser queryStringParser = new QueryStringParser();
    private static final SimpleStringParser contentTypeParser = new ContentTypeParser();
    private static final SimpleStringParser routerParser = new WWMLParser();

    public static SimpleStringParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public static SimpleStringParser queryStringParser() {
        return queryStringParser;
    }

    public static SimpleStringParser contentTypeParser() {
        return contentTypeParser;
    }

    public static SimpleStringParser routerParser() {
        return routerParser;
    }

    private KeyValueParserFactory() {}
}