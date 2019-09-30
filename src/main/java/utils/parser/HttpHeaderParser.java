package utils.parser;

public class HttpHeaderParser extends SimpleStringParser {
    protected HttpHeaderParser() {
        super("(\\r|\\n|\\r\\n)", ": ");
    }
}