package mapper;

import java.util.HashMap;
import java.util.Map;

public class QueryParams {
    private Map<String, String> queryParams = new HashMap<>();

    public QueryParams(String url) {
        String[] queryParams = extractQueryParams(url);
        for (String queryParam : queryParams) {
            String[] token = queryParam.split("=");
            this.queryParams.put(token[0], token[1]);
        }
    }

    private String[] extractQueryParams(String url) {
        String[] split = url.split("\\?");
        return split[1].split("&");
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
