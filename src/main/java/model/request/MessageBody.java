package model.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> extractPostParameters() {
        return parameters;
    }
}
