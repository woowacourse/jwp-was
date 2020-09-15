package http;

import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.stream.Stream;

public class RequestBody {
    private static final String BODY_DELIMITER = "&";
    private static final String BODY_KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> values;

    public RequestBody(Map<String, String> values) {
        this.values = values;
    }

    public static RequestBody from(final String values) {
        return Stream.of(values.split(BODY_DELIMITER))
                .map(value -> value.split(BODY_KEY_VALUE_DELIMITER))
                .collect(collectingAndThen(toMap(value -> value[0], value -> value[1]), RequestBody::new));
    }

    public String getValue(final String key) {
        return values.get(key);
    }
}
