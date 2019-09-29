package webserver.http.request;

import utils.HttpRequestUtils;

import java.util.Collections;
import java.util.Map;

public class RequestBody {


    private Map<String, String> bodies;

    public RequestBody(String rawBody) {
        bodies = parseBody(rawBody);
    }

    private Map<String, String> parseBody(String rawBody) {
        if (rawBody != null && rawBody.length() > 0) {
            return HttpRequestUtils.parseParamToMap(rawBody);
        }
        return Collections.emptyMap();
    }

    public Map<String, String> getBodies() {
        return bodies;
    }

    public String getBody(String key) {
        return bodies.get(key);
    }
}
