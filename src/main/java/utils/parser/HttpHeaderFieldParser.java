package utils.parser;

public class HttpHeaderFieldParser extends SimpleStringParser {
    protected HttpHeaderFieldParser() {
        super(";", "=");
    }
}