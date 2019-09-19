package webserver.domain;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
        return URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
    }

    public Map<String, String> getQueries() {
        return Collections.unmodifiableMap(queries);
    }

    public String getValue(final String key) {
        return queries.getOrDefault(key, "");
    }
}
