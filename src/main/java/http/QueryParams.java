package http;

import java.util.HashMap;
import java.util.Map;

public class QueryParams {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int CORRECT_PARAMS_SIZE = 2;

    private static final String QUERY_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";
    private static final String EMPTY = "";

    private Map<String, String> params;

    public QueryParams(String params) {
        this.params = splitQueryParams(params);
    }

    private Map<String, String> splitQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        for (String param : query.split(QUERY_DELIMITER)) {
            String[] splicedParam = param.split(PARAM_DELIMITER);
            params.put(splicedParam[KEY_INDEX], extractValueFrom(splicedParam));
        }
        return params;
    }

    private String extractValueFrom(String[] splicedParam) {
        return (splicedParam.length != CORRECT_PARAMS_SIZE) ? EMPTY : splicedParam[VALUE_INDEX];
    }

    public String getParam(String key) {
        return params.get(key);
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "params=" + params +
                '}';
    }
}
