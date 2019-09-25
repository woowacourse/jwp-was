package webserver.http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParams {
    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY = "";

    private Map<String, String> queryParams;

    private QueryStringParams(final Map<String, String> queryParams) {
        this.queryParams = Collections.unmodifiableMap(queryParams);
    }

    public static QueryStringParams of(final String queryString) {
        return new QueryStringParams(extractQueryParams(queryString));
    }

    private static Map<String, String> extractQueryParams(final String queryString) {
        if (EMPTY.equals(queryString)) {
            return Collections.emptyMap();
        }

        return generateQueryParams(queryString);
    }

    private static Map<String, String> generateQueryParams(final String queryString) {
        return Arrays.stream(queryString.split(QUERY_PARAM_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(param -> param[0], QueryStringParams::parseParamValue))
                ;
    }

    private static String parseParamValue(final String[] tokens) {
        if (tokens.length == 1) {
            return EMPTY;
        }

        return tokens[1];
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
