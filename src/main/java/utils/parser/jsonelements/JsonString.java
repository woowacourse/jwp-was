package utils.parser.jsonelements;

public final class JsonString extends JsonValue<String> {
    public JsonString(String val) {
        super(val);
    }

    @Override
    public String toString() {
        return "\"" + super.val + "\"";
    }
}