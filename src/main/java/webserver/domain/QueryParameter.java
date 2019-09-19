package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParameter {
    private final Map<String, String> queries;

    public QueryParameter(final String rawQuery) {
        this.queries = extractQueries(rawQuery);
    }

    public void putByRawQueries(final String rawQueries) {
        this.queries.putAll(extractQueries(rawQueries));
    }

    private Map<String, String> extractQueries(final String rawQuery) {
        return Arrays.stream(rawQuery.split("&"))
                .map(query -> query.split("=", 2))
                .filter(this::queryFilter)
                .collect(Collectors.toMap(array -> urlDecode(array[0]), array -> urlDecode(array[1])));
    }

    private boolean queryFilter(final String[] keyValue) {
        return keyValue.length == 2 && !keyValue[0].isEmpty() && !keyValue[1].isEmpty();
    }

    private String urlDecode(final String encodedString) {
        try {
            return URLDecoder.decode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Map<String, String> getQueries() {
        return Collections.unmodifiableMap(queries);
    }

    public String getValue(final String key) {
        return queries.getOrDefault(key, "");
    }
}
