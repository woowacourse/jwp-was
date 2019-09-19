package utils.parser;

public class HttpHeaderFieldsParser extends EncodedURLParser {
    protected HttpHeaderFieldsParser() {
        super("\n", ": ");
    }
}