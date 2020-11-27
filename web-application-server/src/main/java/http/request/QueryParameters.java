package http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class QueryParameters {
    private static final QueryParameters DEFAULT_QUERY_PARAMETERS =
        new QueryParameters(Collections.unmodifiableMap(new HashMap<>()));
    private static final String QUERY_SPLITTER = "&";
    private static final String KEY_VALUE_SPLITTER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> queryParams;

    public static QueryParameters of(String queries) {
        Map<String, String> queryParams = Arrays.stream(queries.split(QUERY_SPLITTER))
            .map(query -> query.split(KEY_VALUE_SPLITTER))
            .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX]));
        return new QueryParameters(queryParams);
    }

    public static QueryParameters empty() {
        return DEFAULT_QUERY_PARAMETERS;
    }

    public QueryParameters(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Nullable
    public String getParameter(String name) {
        return queryParams.get(name);
    }
}
