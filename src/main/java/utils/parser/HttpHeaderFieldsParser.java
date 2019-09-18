package utils.parser;

public class HttpHeaderFieldsParser extends StringChunkParser {
    protected HttpHeaderFieldsParser() {
        super("\n", ": ");
    }
}