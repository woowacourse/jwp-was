package http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParams {
    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private Map<String, String> queryParams;

    private HttpRequestParams(final Map<String, String> queryParams) {
        this.queryParams = Collections.unmodifiableMap(queryParams);
    }

    public static HttpRequestParams init() {
        return new HttpRequestParams(new HashMap<>());
    }

    public static HttpRequestParams of(final String queryString) {
        return new HttpRequestParams(extractQueryParams(queryString));
    }

    private static Map<String, String> extractQueryParams(final String queryString) {
        if (queryString.equals("")) {
            return Collections.emptyMap();
        }
        return generateQueryParams(queryString);
    }

    private static Map<String, String> generateQueryParams(final String queryString) {
        return Collections.unmodifiableMap(Arrays.stream(queryString.split(QUERY_PARAM_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(tokens -> tokens[0], tokens -> tokens[1])));
    }

    public String get(final String key) {
        return queryParams.get(key);
    }

    @Override
    public String toString() {
        return "HttpRequestParams{" +
                "queryParams=" + queryParams +
                '}';
    }
}
