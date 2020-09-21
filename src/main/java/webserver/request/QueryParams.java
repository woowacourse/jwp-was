package webserver.request;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryParams {
    private static final String QUERY_PARAM_INDICATOR = "\\?";
    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> queryParams;

    private QueryParams(final Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public static QueryParams of(String path) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        if (!path.contains("?")) {
            return new QueryParams(queryParams);
        }
        String queryString = path.split(QUERY_PARAM_INDICATOR)[1];
        String[] params = queryString.split(QUERY_PARAM_DELIMITER);
        for (String param : params) {
            String[] attribute = param.split(KEY_VALUE_DELIMITER);
            queryParams.put(attribute[0], attribute[1]);
        }
        return new QueryParams(queryParams);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getParam(final String key) {
        return queryParams.getOrDefault(key, null);
    }
}
