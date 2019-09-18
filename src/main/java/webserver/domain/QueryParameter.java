package webserver.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParameter {
    private final Map<String, String> queries;

    public QueryParameter(final String rawQuery) {
        this.queries = Collections.unmodifiableMap(
                Arrays.stream(rawQuery.split("&"))
                        .map(query -> query.split("=", 2))
                        .filter(query -> !query[0].isEmpty())
                        .collect(Collectors.toMap(str -> str[0], str -> str[1]))
        );
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public String getValue(final String key) {
        return queries.getOrDefault(key, "");
    }
}
