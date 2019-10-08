package utils.parser.jsonelements;

import java.util.Objects;

public abstract class JsonValue<T> {
    protected static final String TAB = "    ";

    protected T val;

    public JsonValue(T val) {
        this.val = val;
    }

    private JsonValue() {}

    public T val() {
        return val;
    };

    public int size() {
        return 1;
    }

    public String serialize() {
        return toString();
    }

    @Override
    public String toString() {
        return this.val.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonValue)) {
            return false;
        }
        final JsonValue<?> rhs = (JsonValue<?>) o;
        return Objects.equals(this.val, rhs.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }
}