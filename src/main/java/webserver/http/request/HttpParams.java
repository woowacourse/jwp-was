package webserver.http.request;

import static java.util.Collections.*;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;

public class HttpParams {
    private static final String PARAM_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> params;

    private HttpParams(Map<String, String> params) {
        this.params = params;
    }

    public static HttpParams of(String queryParams) {
        if (queryParams.isEmpty()) {
            return new HttpParams(emptyMap());
        }

        Map<String, String> params = Arrays.stream(queryParams.split(PARAM_DELIMITER))
                .collect(toMap(
                        queryParam -> queryParam.split(VALUE_DELIMITER)[KEY],
                        queryParam -> queryParam.split(VALUE_DELIMITER)[VALUE]
                ));

        return new HttpParams(params);
    }

    public String get(String key) {
        return params.get(key);
    }
}
