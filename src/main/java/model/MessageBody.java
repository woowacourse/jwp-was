package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MessageBody {

    private static final String PARAMETER_DELIMITER = "&";
    private static final String PARAMETER_KEY_VALUE_SEPARATOR = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> parameters;

    private MessageBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static MessageBody of(String messageBody) {
        Map<String, String> parameters = new HashMap<>();

        String[] splitParameter = messageBody.split(PARAMETER_DELIMITER);
        Arrays.stream(splitParameter)
            .forEach(p -> {
                String key = p.split(PARAMETER_KEY_VALUE_SEPARATOR)[KEY_INDEX];
                String value = p.split(PARAMETER_KEY_VALUE_SEPARATOR)[VALUE_INDEX];

                parameters.put(key, value);
            });

        return new MessageBody(Collections.unmodifiableMap(parameters));
    }

    public Map<String, String> extractParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageBody that = (MessageBody) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
