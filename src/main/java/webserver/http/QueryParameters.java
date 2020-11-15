package webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryParameters {
    private static final String CONTENT_DELIMITER = "&";
    private static final String DATA_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> queryParameters;
    private final QueryParametersState queryParametersState;

    private QueryParameters(Map<String, String> queryParameters, QueryParametersState queryParametersState) {
        this.queryParameters = queryParameters;
        this.queryParametersState = queryParametersState;
    }

    public static QueryParameters notEmptyQueryParameters(String queryString) {
        Map<String, String> queryParameters = initQueryParameters(queryString);
        return new QueryParameters(queryParameters, QueryParametersState.NOT_EMPTY);
    }

    private static Map<String, String> initQueryParameters(String queryString) {
        Map<String, String> queryParameters = new HashMap<>();
        String[] contents = queryString.split(CONTENT_DELIMITER);
        for (String content : contents) {
            String[] data = content.split(DATA_DELIMITER);
            queryParameters.put(data[KEY_INDEX], data[VALUE_INDEX]);
        }
        return queryParameters;
    }

    public static QueryParameters emptyQueryParameters() {
        return new QueryParameters(Collections.emptyMap(), QueryParametersState.EMPTY);
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public QueryParametersState getQueryParametersState() {
        return queryParametersState;
    }
}
