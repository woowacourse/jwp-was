package utils.parser.simple;

public class ContentTypeParser extends SimpleStringParser {
    protected ContentTypeParser() {
        super(";", "=");
    }
}