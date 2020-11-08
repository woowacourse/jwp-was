package http;

import exception.DuplicateParamsException;

import java.util.Arrays;
import java.util.Collections;
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

        this.queryParams = convertQueryParams(requestUri);
    }

    private Map<String, String> convertQueryParams(String requestUri) {
        String[] splitUri = requestUri.split(URL_PARAMETER_DELIMITER);

        if (splitUri.length == 1) {
            return Collections.emptyMap();
        }

        String queryString = splitUri[1];
        String[] paramPairs = queryString.split(PARAMETER_DELIMITER);

        try {
            return Arrays.stream(paramPairs)
                    .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                    .filter(it -> it.length == 2)
                    .collect(Collectors.toMap(it -> it[0], it -> it[1]));
        } catch (IllegalStateException e) {
            throw new DuplicateParamsException("Query String에 중복된 키가 있습니다.");
        }
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
