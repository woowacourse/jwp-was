package utils.parser;

public class HttpHeaderFieldAttrParser extends SimpleStringParser {
    protected HttpHeaderFieldAttrParser() {
        super(";", "=");
    }
}