package http.request;

import java.util.Collections;
import java.util.Map;

public class QueryParameters {
    private Map<String, String> queryParameters;

    public QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getParameter(String key) {
        return queryParameters.get(key);
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
