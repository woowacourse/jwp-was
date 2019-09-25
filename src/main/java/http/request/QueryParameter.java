package http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryParameter {
    private Map<String, String> parameters;

    private QueryParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryParameter of(String queryString) {
        if (queryString.contains("&")) {
            String[] tokens = queryString.split("&");
            Map<String, String> querys = new HashMap<>();
            Arrays.stream(tokens)
                    .forEach(s -> querys.put(s.split("=")[0], s.split("=")[1]));

            return new QueryParameter(querys);
        }
        return new QueryParameter(Collections.singletonMap(
                queryString.split("=")[0],
                queryString.split("=")[1]));
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
