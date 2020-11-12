package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParameters {
    private static final Map<String, String> DEFAULT_QUERY_PARAMETERS = Collections.unmodifiableMap(new HashMap<>());

    private Map<String, String> queryParams;

    public static QueryParameters of(String queries) {
        Map<String, String> queryParams = Arrays.stream(queries.split("&"))
            .map(query -> query.split("="))
            .collect(Collectors.toMap(
                pair -> pair[0], pair -> pair[1]
            ));
        return new QueryParameters(queryParams);
    }

    public static QueryParameters empty() {
        return new QueryParameters(DEFAULT_QUERY_PARAMETERS);
    }

    public QueryParameters(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public String getParameter(String name) {
        return queryParams.get(name);
    }
}
