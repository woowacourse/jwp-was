package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryParamsParser {

    public static Map<String, String> parse(String queryString) {
        Map<String, String> queryParams = new HashMap<>();

        String[] queryTokens = queryString.split("&");
        Arrays.stream(queryTokens).forEach(queryToken -> {
            String[] queryParamToken = queryToken.split("=");
            String name = queryParamToken[0].trim();
            String value = queryParamToken[1].trim();
            queryParams.put(name, value);
        });
        return queryParams;
    }
}
