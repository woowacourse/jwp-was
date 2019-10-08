package utils.parser.jsonelements;

import utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JsonObject extends JsonValue<Map<String, ? extends JsonValue<?>>> {
    public JsonObject() {
        super(Collections.emptyMap());
    }

    public JsonObject(Map<String, ? extends JsonValue<?>> val) {
        super(val);
    }

    public JsonObject(String k, JsonValue<?> v) {
        super(new HashMap<String, JsonValue<?>>() {{
            this.put(k, v);
        }});
    }

    public JsonValue<?> get(String key) {
        return super.val.get(key);
    }

    public Stream<? extends Map.Entry<String, ? extends JsonValue<?>>> stream() {
        return super.val.entrySet().stream();
    }

    @Override
    public int size() {
        return super.val.size();
    }

    public boolean isEmpty() {
        return super.val.isEmpty();
    }

    @Override
    public String serialize() {
        switch (size()) {
            case 0:
                return "{}";
            case 1:
                return super.val.entrySet()
                                .stream()
                                .map(o -> String.format("{\"%s\":%s}", o.getKey(), o.getValue().serialize()))
                                .collect(Collectors.joining());
            default:
                return "{" + super.val.entrySet()
                                        .stream()
                                        .map(o -> String.format("\"%s\":%s", o.getKey(), o.getValue().serialize()))
                                        .reduce((a, b) -> a + "," + b)
                                        .get() + "}";
        }
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int depth) {
        if (isEmpty()) {
            return "{}";
        }
        if ((size() == 1) && super.val.values().stream().allMatch(x -> x.size() == 1)) {
            return super.val.entrySet()
                            .stream()
                            .map(o -> String.format("{\"%s\": %s}", o.getKey(), o.getValue()))
                            .collect(Collectors.joining());
        }
        return toStringMultipleAttributes(depth);
    }

    private String toStringMultipleAttributes(int depth) {
        return "{\r\n" +
                stream().map(x -> attributeToString(x.getKey(), x.getValue(), depth))
                        .reduce((a, b) -> a + ",\r\n" + b)
                        .get() +
                "\r\n" + StringUtils.repeatString(TAB, depth) + "}";
    }

    private String attributeToString(String key, JsonValue<?> value, int depth) {
        return (value instanceof JsonObject)
                ? String.format(
                        "%s\"%s\": %s",
                        StringUtils.repeatString(TAB, depth + 1),
                        key,
                        ((JsonObject) value).toString(depth + 1)
                )
                : String.format("%s\"%s\": %s", StringUtils.repeatString(TAB, depth + 1), key, value);
    }
}