package webserver.request.requestline;

import java.util.HashMap;
import java.util.Map;

public class QueryParams {

    private Map<String, String> params = new HashMap<>();

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public String findParam(String key) {
        return params.get(key);
    }
}
