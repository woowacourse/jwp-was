package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static http.RequestUri.URL_PARAMETER_DELIMITER;

public class QueryParams {
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> queryParams;

    public QueryParams(String requestUri) {
        if (requestUri == null || requestUri.isEmpty()) {
            throw new IllegalArgumentException("URI가 비어 있습니다.");
        }

        this.queryParams = parseQueryParams(requestUri);
    }

    private Map<String, String> parseQueryParams(String requestUri) {
        String[] splitUri = requestUri.split(URL_PARAMETER_DELIMITER);

        if (splitUri.length == 1) {
            return new HashMap<>();
        }

        String queryString = splitUri[1];
        String[] paramPairs = queryString.split(PARAMETER_DELIMITER);

        return Arrays.stream(paramPairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
