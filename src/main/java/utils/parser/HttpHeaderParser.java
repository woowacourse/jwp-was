package utils.parser;

public final class HttpHeaderParser extends SimpleStringParser {
    protected HttpHeaderParser() {
        super("(\\r|\\n|\\r\\n)", ": ");
    }
}