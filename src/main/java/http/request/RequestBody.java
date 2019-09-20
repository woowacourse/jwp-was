package http.request;

import http.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> entity;

    private RequestBody(Map<String, String> entity) {
        this.entity = entity;
    }

    public static RequestBody of(String requestBodyString) {
        if (requestBodyString.isEmpty()) {
            return new RequestBody(new HashMap<>());
        }
        return new RequestBody(HttpUtils.parseQuery(requestBodyString));
    }

    public String getEntityValue(String key) {
        return entity.get(key);
    }
}
