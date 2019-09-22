package utils.parser;

public class JsonAttributesParser extends SimpleStringParser {
    protected JsonAttributesParser() {
        super(",", ":");
    }
}