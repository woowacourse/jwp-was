package mapper;

import java.util.HashMap;
import java.util.Map;

public class QueryParams {
    private final static String QUERY_PARAM_DELIMITER = "=";
    private final static String QUERY_PARAMS_DELIMITER = "&";
    private final static String URL_DELIMITER = "\\?";
    private final static String[] EMPTY_STRINGS = new String[]{};

    private Map<String, String> queryParams = new HashMap<>();

    public QueryParams(String url) {
        String[] queryParams = extractQueryParams(url);
        for (String queryParam : queryParams) {
            String[] token = queryParam.split(QUERY_PARAM_DELIMITER);
            this.queryParams.put(token[0], token[1]);
        }
    }

    private String[] extractQueryParams(String url) {
        String[] split = url.split(URL_DELIMITER);
        if (split.length == 1) {
            return EMPTY_STRINGS;
        }
        return split[1].split(QUERY_PARAMS_DELIMITER);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
