package utils.parser;

/** WooWaMarkupLanguage for setting routes
 *
 * path -> controller.method */
public class WWMLParser extends SimpleStringParser {
    protected WWMLParser() {
        super("(\\r|\\n|\\r\\n)+", "->");
    }
}