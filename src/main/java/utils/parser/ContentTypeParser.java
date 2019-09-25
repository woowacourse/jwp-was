package utils.parser;

public class ContentTypeParser extends SimpleStringParser {
    protected ContentTypeParser() {
        super(";", "=");
    }
}