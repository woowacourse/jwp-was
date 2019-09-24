package http.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtils {
    private static final String QUERY_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";

    public static Map<String, String> parseQuery(String queryString) {
        return Arrays.stream(queryString.split(QUERY_DELIMITER))
                .map(q -> q.split(PARAM_DELIMITER))
                .collect(Collectors.toMap(token -> token[0], token -> token[1]));
    }
}
