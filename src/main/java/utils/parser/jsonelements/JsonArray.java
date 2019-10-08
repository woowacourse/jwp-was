package utils.parser.jsonelements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class JsonArray extends JsonValue<List<JsonValue<?>>> {
    public JsonArray() {
        super(Collections.emptyList());
    }

    public JsonArray(List<JsonValue<?>> val) {
        super(val);
    }

    public JsonArray(JsonValue<?>... elements) {
        this(Arrays.asList(elements));
    }

    Optional<JsonValue<?>> get(int i) {
        return Optional.of(super.val)
                        .filter(val -> (0 <= i) && (i < val.size()))
                        .map(val -> val.get(i));
    }

    public Stream<JsonValue<?>> stream() {
        return super.val.stream();
    }

    public int size() {
        return super.val.size();
    }

    public boolean isEmpty() {
        return super.val.isEmpty();
    }

    @Override
    public String serialize() {
        if (super.val.size() <= 1) {
            return toString();
        }
        return "[" + super.val.stream()
                                .map(JsonValue::serialize)
                                .reduce((a, b) -> a + "," + b)
                                .get() + "]";
    }

    @Override
    public String toString() {
        switch (super.val.size()) {
            case 0:
                return "[]";
            case 1:
                return "[" + super.val.get(0) + "]";
            default:
                return "[" + super.val.stream()
                                        .map(JsonValue::toString)
                                        .reduce((a, b) -> a + ", " + b)
                                        .get() + "]";
        }
    }
}