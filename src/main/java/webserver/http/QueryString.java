package webserver.http;

import exception.InvalidHttpMessageException;
import exception.InvalidParameterException;
import utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryString {
    private static final String PARAMETERS_DELIMITER = "&";
    private static final String PARAMETER_VALUE_DELIMITER = "=";

    private final Map<String, String> params;

    private QueryString(Map<String, String> params) {
        this.params = params;
    }

    public static QueryString from(String queryString) {
        Objects.requireNonNull(queryString);

        if (queryString.isEmpty()) {
            return empty();
        }

        Map<String, String> params = createParams(queryString);

        return new QueryString(params);
    }

    public static QueryString empty() {
        return new QueryString(Collections.emptyMap());
    }

    private static Map<String, String> createParams(String queryString) {
        Map<String, String> params = new HashMap<>();

        for (String parameter : queryString.split(PARAMETERS_DELIMITER)) {
            String[] token = parameter.split(PARAMETER_VALUE_DELIMITER);
            if (token.length != 2) {
                throw new InvalidParameterException(parameter);
            }

            String parameterKey = token[0].trim();
            if (parameterKey.isEmpty()) {
                throw new InvalidParameterException(parameter);
            }
            String parameterValue = token[1].trim();

            params.put(parameterKey, parameterValue);
        }

        return params;
    }

    public String getParameterValue(String parameterKey) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(parameterKey), parameterKey);

        return this.params.get(parameterKey);
    }

    public boolean isNotEmpty() {
        return !this.params.isEmpty();
    }

    public String toHttpMessage() {
        return this.params.entrySet().stream()
                .map(entry -> entry.getKey() + PARAMETER_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(PARAMETERS_DELIMITER));
    }
}
