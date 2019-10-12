package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryParameter {
    private static final String UTF_8 = "UTF-8";
    private static final String EMPTY = "";
    private static final String QUERY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private final Map<String, String> queries;

    public QueryParameter(final String rawQuery) {
        this.queries = extractQueries(rawQuery);
    }

    void putByRawQueries(final String rawQueries) {
        this.queries.putAll(extractQueries(rawQueries));
    }

    private Map<String, String> extractQueries(final String rawQuery) {
        final Map<String, String> queries = new HashMap<>();

        if (Objects.isNull(rawQuery)) {
            return queries;
        }

        final String[] rawList = rawQuery.split(QUERY_DELIMITER);
        for (final String rawKeyValue : rawList) {
            final String[] tuple = rawKeyValue.split(KEY_VALUE_DELIMITER, 2);
            if (isValidTuple(tuple)) {
                queries.put(urlDecode(tuple[KEY_INDEX]), urlDecode(tuple[VALUE_INDEX]));
            }
        }
        return queries;
    }

    private boolean isValidTuple(final String[] keyValue) {
        return keyValue.length == 2 && !keyValue[0].isEmpty() && !keyValue[1].isEmpty();
    }

    private String urlDecode(final String encodedString) {
        try {
            return URLDecoder.decode(encodedString, UTF_8);
        } catch (final UnsupportedEncodingException ignored) {
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
