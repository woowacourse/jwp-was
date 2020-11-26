package http;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class QueryParams {
    private static final String DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String DEFAULT_VALUE = "";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int FIRST_PARAM = 0;

    private final Map<String, List<String>> queryParameters;

    private QueryParams(Map<String, List<String>> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static QueryParams parse(String query) {
        return Stream.of(query.split(DELIMITER))
            .map(it -> it.split(KEY_VALUE_DELIMITER))
            .collect(collectingAndThen(groupingBy(
                it -> it[KEY_INDEX],
                mapping(it -> it.length > VALUE_INDEX ? it[VALUE_INDEX] : DEFAULT_VALUE, toList())
            ), QueryParams::new));
    }

    public static QueryParams empty() {
        return new QueryParams(new HashMap<>());
    }

    public List<String> get(String key) {
        return queryParameters.get(key);
    }

    public String getFirst(String key) {
        return Optional.ofNullable(get(key))
            .map(it -> it.get(FIRST_PARAM))
            .orElse(DEFAULT_VALUE);
    }
}
