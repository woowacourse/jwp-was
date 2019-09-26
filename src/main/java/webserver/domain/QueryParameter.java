package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryParameter {
    private static final String UTF_8 = "UTF-8";
    private static final String EMPTY = "";
    private static final String QUERY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private final Map<String, String> queries;

    public QueryParameter(final String rawQuery) {
        this.queries = extractQueries(rawQuery);
    }

    void putByRawQueries(final String rawQueries) {
        this.queries.putAll(extractQueries(rawQueries));
    }

    private Map<String, String> extractQueries(final String rawQuery) {
        if (Objects.isNull(rawQuery)) {
            return new HashMap<>();
        }
        return Arrays.stream(rawQuery.split(QUERY_DELIMITER))
                .map(query -> query.split(KEY_VALUE_DELIMITER, 2))
                .filter(this::queryFilter)
                .collect(Collectors.toMap(array -> urlDecode(array[0]), array -> urlDecode(array[1])));
    }

    private boolean queryFilter(final String[] keyValue) {
        return keyValue.length == 2 && !keyValue[0].isEmpty() && !keyValue[1].isEmpty();
    }

    private String urlDecode(final String encodedString) {
        try {
            return URLDecoder.decode(encodedString, UTF_8);
        } catch (final UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public String getValue(final String key) {
        return queries.getOrDefault(key, EMPTY);
    }

    @Override
    public String toString() {
        return queries.toString();
    }
}
