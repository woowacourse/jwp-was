package http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryParameter {
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private Map<String, String> parameters;

    private QueryParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryParameter of(String queryString) {
        if (queryString.contains(QUERY_STRING_DELIMITER)) {
            String[] tokens = queryString.split(QUERY_STRING_DELIMITER);
            Map<String, String> querys = new HashMap<>();
            Arrays.stream(tokens)
                    .forEach(s -> querys.put(s.split(KEY_VALUE_DELIMITER)[0], s.split(KEY_VALUE_DELIMITER)[1]));

            return new QueryParameter(querys);
        }
        return new QueryParameter(Collections.singletonMap(
                queryString.split(KEY_VALUE_DELIMITER)[0],
                queryString.split(KEY_VALUE_DELIMITER)[1]));
    }

    public static QueryParameter empty() {
        return new QueryParameter(Collections.emptyMap());
    }

    public String getValue(String key) {
        return parameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryParameter that = (QueryParameter) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
