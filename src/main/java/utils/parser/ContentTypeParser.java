package utils.parser;

public class ContentTypeParser extends PlainStringParser {
    protected ContentTypeParser() {
        super(";", "=");
    }
}