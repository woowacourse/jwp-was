package webserver;

import exception.InvalidParameterException;
import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            return new QueryString(new HashMap<>());
        }

        Map<String, String> params = createParams(queryString);

        return new QueryString(params);
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
        StringUtils.validateNonNullAndNotEmpty(parameterKey);

        String parameterValue = this.params.get(parameterKey);
        if (Objects.isNull(parameterValue)) {
            throw new NullPointerException(parameterKey + "에 해당하는 Parameter Key가 없습니다!");
        }

        return parameterValue;
    }
}
