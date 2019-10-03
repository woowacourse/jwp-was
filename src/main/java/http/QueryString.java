package http;

import http.exception.NotFoundParameterException;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String QUERY_DELIMITER = "=";

    private Map<String, String> parameters = new HashMap<>();

    public QueryString() {
    }

    public QueryString(String queryString) {
        parseQueryString(queryString);
    }

    public void add(String queryString) {
        parseQueryString(queryString);
    }

    private void parseQueryString(String queryString) {
        String[] queryStringTokens = queryString.split(PARAMETER_DELIMITER);
        for (String query : queryStringTokens) {
            String[] queryTokens = query.split(QUERY_DELIMITER);
            parameters.put(queryTokens[0], queryTokens[1]);
        }
    }

    public String getParameter(String key) {
        String parameter = parameters.get(key);
        if (parameter == null) {
            throw new NotFoundParameterException();
        }

        return parameters.get(key);
    }
}
