package utils;

import model.User;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = 1;

    public static User parseBody(String requestBody) {
        final String[] pairs = requestBody.split(PARAMETER_DELIMITER);

        final Map<String, String> parameters = Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));

        return new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
    }
}
