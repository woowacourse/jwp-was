package webserver.http;

import java.util.Map;

public class URL {
    private static final String QUERY_PARAMETER_DELIMITER = "?";
    private static final String QUERY_PARAMETER_REGEX = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAMETER_INDEX = 1;

    private String path;
    private QueryParameters queryParameters;

    private URL(String path) {
        this.path = path;
    }

    private URL(String path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public static URL of(String url) {
        if (url.contains(QUERY_PARAMETER_DELIMITER)) {
            String[] urls = url.split(QUERY_PARAMETER_REGEX);
            QueryParameters queryParameters = QueryParameters.notEmptyQueryParameters(urls[QUERY_PARAMETER_INDEX]);
            return new URL(urls[PATH_INDEX], queryParameters);
        }
        QueryParameters queryParameters = QueryParameters.emptyQueryParameters();
        return new URL(url, queryParameters);
    }

    public boolean isEndsWith(String path) {
        return this.path.endsWith(path);
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters.getQueryParameters();
    }

    public String getPath() {
        return path;
    }

    public QueryParametersState hasQueryParameters() {
        return queryParameters.getQueryParametersState();
    }
}
