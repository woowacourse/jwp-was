package http;

import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.stream.Stream;

public class QueryParameters {
    private static final String QUERY_DELIMITER = "&";
    private static final String QUERY_KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> parameters;

    private QueryParameters(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryParameters from(final String queries) {
        return Stream.of(queries.split(QUERY_DELIMITER))
                .map(query -> query.split(QUERY_KEY_VALUE_DELIMITER))
                .collect(collectingAndThen(toMap(query -> query[0], query -> query[1]), QueryParameters::new));
    }

    public String getParameter(final String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "QueryParameters{" +
                "parameters=" + parameters +
                '}';
    }
}
