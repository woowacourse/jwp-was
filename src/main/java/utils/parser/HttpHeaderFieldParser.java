package utils.parser;

public final class HttpHeaderFieldParser extends SimpleStringParser {
    protected HttpHeaderFieldParser() {
        super(";", "=");
    }
}