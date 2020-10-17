package model.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (messageBody.isEmpty()){
            return new MessageBody(Collections.unmodifiableMap(Collections.emptyMap()));
        }
        Map<String, String> parameters = Arrays.stream(messageBody.split(PARAMETER_DELIMITER))
            .map(it -> it.split(PARAMETER_KEY_VALUE_SEPARATOR))
            .collect(Collectors.toMap(it -> it[KEY_INDEX], it -> it[VALUE_INDEX]));

        return new MessageBody(Collections.unmodifiableMap(parameters));
    }

    public Map<String, String> extractBodyParameters() {
        return parameters;
    }
}
