package http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParams {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int CORRECT_PARAMS_SIZE = 2;
    private static final String QUERY_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";
    private static final String EMPTY = "";

    private Map<String, Params> params;

    private QueryParams(Map<String, Params> params) {
        this.params = params;
    }

    public static QueryParams of(String query) {
        return new QueryParams(splitQueryParams(query));
    }

    private static Map<String, Params> splitQueryParams(String query) {
        Map<String, Params> queryParams = new HashMap<>();

        for (String param : query.split(QUERY_DELIMITER)) {
            String[] splicedParam = param.split(PARAM_DELIMITER);
            String key = splicedParam[KEY_INDEX];
            String value = extractValueFrom(splicedParam);

            Params params = getParamsOrDefault(queryParams, key);
            params.add(value);
        }
        return queryParams;
    }

    private static Params getParamsOrDefault(Map<String, Params> queryParams, String key) {
        if (!queryParams.containsKey(key)) {
            Params params = new Params();
            queryParams.put(key, params);
            return params;
        }
        return queryParams.get(key);
    }

    private static String extractValueFrom(String[] splicedParam) {
        return (splicedParam.length != CORRECT_PARAMS_SIZE) ? EMPTY : splicedParam[VALUE_INDEX];
    }

    public String getParam(String key) {
        return params.get(key).getValue();
    }

    public List<String> getParams(String key) {
        return params.get(key).getValues();
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "params=" + params +
                '}';
    }
}
