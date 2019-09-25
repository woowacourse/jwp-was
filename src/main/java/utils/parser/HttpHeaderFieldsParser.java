package utils.parser;

public class HttpHeaderFieldsParser extends SimpleStringParser {
    protected HttpHeaderFieldsParser() {
        super("(\\r|\\n|\\r\\n)", ": ");
    }
}