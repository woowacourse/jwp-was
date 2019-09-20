package utils.parser;

public class HttpHeaderFieldsParser extends URLParser {
    protected HttpHeaderFieldsParser() {
        super("(\\r\\n|\\r|\\n)", ": ");
    }
}