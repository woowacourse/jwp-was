package utils.parser.jsonelements;

public final class JsonNull extends JsonValue<Void> {
    private static final JsonNull NULL = new JsonNull();

    public static JsonNull get() {
        return NULL;
    }

    private JsonNull() { super(null); }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "null";
    }
}