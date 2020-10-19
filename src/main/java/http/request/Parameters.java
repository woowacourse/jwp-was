package http.request;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import utils.StringUtils;

public class Parameters {
    private final Map<String, String> parameters;

    private Parameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static Parameters from(String queryParameters) {
        Objects.requireNonNull(queryParameters);

        if (queryParameters.isEmpty()) {
            return new Parameters(Collections.emptyMap());
        }
        return new Parameters(StringUtils.readParameters(queryParameters));
    }

    public String getParameterBy(String key) {
        return parameters.get(key);
    }
}
