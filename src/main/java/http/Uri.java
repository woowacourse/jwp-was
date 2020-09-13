package http;

import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.stream.Stream;

public class Uri {
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final String QUERY_PARAMETER_DELIMITER = "&";
    private static final String QUERY_PARAMETER_KEY_VALUE_DELIMITER = "=";

    private final String path;
    private final Map<String, String> queryParameters;

    public Uri(final String uri) {
        String[] splitUri = uri.split(QUERY_STRING_DELIMITER);
        this.path = splitUri[0];
        this.queryParameters = Stream.of(splitUri[1].split(QUERY_PARAMETER_DELIMITER))
                .map(query -> query.split(QUERY_PARAMETER_KEY_VALUE_DELIMITER))
                .collect(toMap(query -> query[0], query -> query[1]));
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public String getParameter(final String key) {
        return queryParameters.get(key);
    }

    @Override
    public String toString() {
        return "Uri{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                '}';
    }
}
