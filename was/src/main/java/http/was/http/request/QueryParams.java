package http.was.http.request;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryParams {
    private final static String QUERY_PARAM_DELIMITER = "=";
    private final static String QUERY_PARAMS_DELIMITER = "&";
    private final static String URL_DELIMITER = "?";

    private Map<String, String> queryParams = new LinkedHashMap<>();

    public QueryParams(String url) {
        String[] queryParams = url.split(QUERY_PARAMS_DELIMITER);
        for (String queryParam : queryParams) {
            String[] token = queryParam.split(QUERY_PARAM_DELIMITER);
            this.queryParams.put(token[0], token[1]);
        }
    }

    public QueryParams() {
    }

    public boolean isEmpty() {
        return queryParams.isEmpty();
    }

    public String getParam(String key) {
        return queryParams.get(key);
    }

    @Override
    public String toString() {
        if (queryParams.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(URL_DELIMITER);
        for (String key : queryParams.keySet()) {
            sb.append(key)
                    .append(QUERY_PARAM_DELIMITER)
                    .append(queryParams.get(key))
                    .append(QUERY_PARAMS_DELIMITER);
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }
}
