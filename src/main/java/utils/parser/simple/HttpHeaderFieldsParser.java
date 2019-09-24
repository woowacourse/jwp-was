package utils.parser.simple;

public class HttpHeaderFieldsParser extends SimpleStringParser {
    protected HttpHeaderFieldsParser() {
        super("(\\r|\\n|\\r\\n)", ": ");
    }
}