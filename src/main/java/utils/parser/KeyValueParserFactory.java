package utils.parser;

public class KeyValueParserFactory {
    private static final JsonParser jsonParser = new JsonParser();
    private static final SimpleStringParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final SimpleStringParser queryStringParser = new QueryStringParser();
    private static final SimpleStringParser httpHeaderFieldAttrParser = new HttpHeaderFieldAttrParser();

    public static JsonParser jsonParser() {
        return jsonParser;
    }

    public static SimpleStringParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public static SimpleStringParser queryStringParser() {
        return queryStringParser;
    }

    public static SimpleStringParser httpHeaderFieldAttrParser() {
        return httpHeaderFieldAttrParser;
    }

    private KeyValueParserFactory() {}
}