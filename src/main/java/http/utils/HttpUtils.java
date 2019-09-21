package http.utils;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    private static final String QUERY_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";

    public static Map<String, String> parseQuery(String queryString) {
        String[] queries = queryString.split(QUERY_DELIMITER);
        Map<String, String> result = new HashMap<>();
        for (String q : queries) {
            String[] token = q.split(PARAM_DELIMITER);
            result.put(token[0], token[1]);
        }

        return result;
    }
}
