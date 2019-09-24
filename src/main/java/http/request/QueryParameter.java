package http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryParameter {
    private Map<String, String> parameters;

    private QueryParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryParameter of(String queryString) {
        String[] tokens = queryString.split("&");

        Map<String, String> querys = new HashMap<>();
        Arrays.stream(tokens)
                .forEach(s -> querys.put(s.split("=")[0], s.split("=")[1]));

        return new QueryParameter(querys);
    }

    public static QueryParameter empty() {
        return new QueryParameter(Collections.emptyMap());
    }

    public String getValue(String key) {
        return parameters.get(key);
    }
}
