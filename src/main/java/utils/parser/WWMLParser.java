package utils.parser;

/**
 * WooWaMarkupLanguage
 * URI ## controller
 */
public class WWMLParser extends SimpleStringParser {
    protected WWMLParser() {
        super("(\\r\\n|\\r|\\n)+", "##");
    }
}