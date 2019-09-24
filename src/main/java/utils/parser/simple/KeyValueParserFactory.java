package utils.parser.simple;

import utils.parser.json.JsonParser;

public class KeyValueParserFactory {
    private static final JsonParser jsonParser = new JsonParser();
    private static final SimpleStringParser httpHeaderFieldsParser = new HttpHeaderFieldsParser();
    private static final SimpleStringParser queryStringParser = new QueryStringParser();
    private static final SimpleStringParser contentTypeParser = new ContentTypeParser();

    public static JsonParser jsonParser() {
        return jsonParser;
    }

    public static SimpleStringParser httpHeaderFieldsParser() {
        return httpHeaderFieldsParser;
    }

    public static SimpleStringParser queryStringParser() {
        return queryStringParser;
    }

    public static SimpleStringParser contentTypeParser() {
        return contentTypeParser;
    }

    private KeyValueParserFactory() {}
}