package utils.parser.json;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonArray extends JsonValue<List<JsonValue<?>>> {
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

    int size() {
        return super.val.size();
    }

    @Override
    public String toString() {
        if (super.val.isEmpty()) {
            return "[]";
        }
        if (super.val.size() == 1) {
            return super.val.stream()
                            .map(el -> "[" + el.toString() + "]")
                            .collect(Collectors.joining());
        }
        return "{" + super.val.stream()
                                .map(JsonValue::toString)
                                .reduce((a, b) -> a + ", " + b)
                                .get() + "}";
    }
}