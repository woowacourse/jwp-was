package http.request;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import utils.StringUtils;

public class QueryParameters {
    private final Map<String, String> queryParameters;

    private QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static QueryParameters from(String queryParameters) {
        Objects.requireNonNull(queryParameters);
        if (queryParameters.isEmpty()) {
            return new QueryParameters(Collections.emptyMap());
        }
        return new QueryParameters(StringUtils.readParameters(queryParameters));
    }

    public String getParameterBy(String key) {
        return queryParameters.get(key);
    }
}
