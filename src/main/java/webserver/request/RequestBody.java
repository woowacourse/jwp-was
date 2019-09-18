package webserver.request;

import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> body;

    public RequestBody(String rawBody) {
        parseBody(rawBody);
    }

    private void parseBody(String rawBody) {
        body = new HashMap<>();
        if (rawBody != null) {
            body = HttpRequestUtils.parseParamToMap(rawBody);
        }
    }

    public String getBody(String key) {
        return body.get(key);
    }
}
