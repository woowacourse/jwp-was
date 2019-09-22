package utils.parser;

import java.util.Objects;
import java.util.function.Predicate;

public class JsonValue<T> {
    private static final JsonValue<Void> NULL = new JsonValue();

    protected T val;

    public static JsonValue<Void> NULL() {
        return NULL;
    }

    public JsonValue(T val) {
        this.val = val;
    }

    private JsonValue() {}

    public T val() {
        return val;
    };

    public boolean test(Predicate<T> cond) {
        return cond.test(this.val);
    }

    public String serialize() {
        return toString();
    }

    @Override
    public String toString() {
        return (this.val != null) ? this.val.toString() : "null";
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