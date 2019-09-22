package utils.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonObject extends JsonValue<Map<String, JsonValue<?>>> {
    public JsonObject() {
        super(Collections.emptyMap());
    }

    public JsonObject(Map<String, JsonValue<?>> val) {
        super(val);
    }

    public JsonObject(String key, JsonValue<?> value) {
        super(new HashMap<String, JsonValue<?>>() {{
            put(key, value);
        }});
    }

    public Optional<JsonValue<?>> get(String key) {
        return Optional.of(super.val).map(val -> val.get(key));
    }

    int size() {
        return super.val.size();
    }

    @Override
    public String toString() {
        if (super.val.isEmpty()) {
            return "{}";
        }
        if (super.val.size() == 1) {
            return super.val.entrySet()
                            .stream()
                            .map(o -> "{\"" + o.getKey() + "\": " + o.getValue().toString() + "}")
                            .collect(Collectors.joining());
        }
        return "{" + super.val.entrySet()
                                .stream()
                                .map(o -> "\"" + o.getKey() + "\": " + o.getValue().toString())
                                .reduce((a, b) -> a + ", " + b)
                                .get() + "}";
    }
}