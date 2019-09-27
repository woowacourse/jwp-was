package http.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryParamsParser {

    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String QUERY_PARAM_DELIMITER = "=";

    public static Map<String, String> parse(String queryString) {
        Map<String, String> queryParams = new HashMap<>();

        String[] queryTokens = queryString.split(QUERY_STRING_DELIMITER);
        Arrays.stream(queryTokens).forEach(queryToken -> {
            String[] queryParamToken = queryToken.split(QUERY_PARAM_DELIMITER);
            String name = queryParamToken[0].trim();
            String value = queryParamToken[1].trim();
            queryParams.put(name, value);
        });
        return queryParams;
    }
}
