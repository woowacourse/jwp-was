package webserver.request;

import java.util.Map;

public class QueryParams {
    private final Map<String, String> queryParams;

    public QueryParams(final Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getParam(final String key) {
        return queryParams.getOrDefault(key, null);
    }
}
