package webserver;

import utils.UrlEncodedParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestLine {

    private static final String DELIMITER = " ";
    private static final String PATH_QUERY_DELIMITER = "?";
    private static final String PATH_QUERY_DELIMITER_REGEX = "\\?";

    private final HttpMethod method;
    private final String requestUri;
    private final String path;
    private final Map<String, String> queries;

    public RequestLine(HttpMethod method, String requestUri, String path, Map<String, String> queries) {
        this.method = Objects.requireNonNull(method);
        this.requestUri = Objects.requireNonNull(requestUri);
        this.path = Objects.requireNonNull(path);
        this.queries = Objects.requireNonNull(queries);
    }

    public static RequestLine from(String requestLine) {
        String[] tokens = requestLine.trim().split(DELIMITER);
        HttpMethod method = HttpMethod.valueOf(tokens[0]);
        String path = tokens[1].split(PATH_QUERY_DELIMITER_REGEX)[0];

        Map<String, String> queries = parseQueryString(tokens[1]);

        return new RequestLine(method, tokens[1], path, queries);
    }

    private static Map<String, String> parseQueryString(String requestUri) {
        Map<String, String> queries = new HashMap<>();
        if (requestUri.contains(PATH_QUERY_DELIMITER)) {
            String[] queryPairs = requestUri.split(PATH_QUERY_DELIMITER_REGEX);
            queries.putAll(UrlEncodedParser.parse(queryPairs[1]));
        }
        return queries;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueries() {
        return queries;
    }
}
