package webserver.request;

import java.util.Collections;
import java.util.Map;

import utils.RequestUtils;

public class QueryParams {
    private final Map<String, String> params;

    private QueryParams(Map<String, String> params) {
        this.params = params;
    }

    public static QueryParams of(String data) {
        return new QueryParams(RequestUtils.getQuery(data));
    }

    public static QueryParams empty() {
        return new QueryParams(Collections.emptyMap());
    }

    public String get(String key) {
        return params.get(key);
    }

    public int length() {
        return params.size();
    }
}
