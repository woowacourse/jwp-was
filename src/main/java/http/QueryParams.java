package http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParams {
    private static final String DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> queryParameters;

    private QueryParams(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static QueryParams parse(String query) {
        return Stream.of(query.split(DELIMITER))
            .map(it -> it.split(KEY_VALUE_DELIMITER))
            .collect(Collectors.collectingAndThen(Collectors.toMap(it -> it[KEY_INDEX], it -> it[VALUE_INDEX]),
                QueryParams::new));
    }

    public static QueryParams empty() {
        return new QueryParams(new HashMap<>());
    }

    public String get(String key) {
        return queryParameters.get(key);
    }
}
