package webserver.controller.request.body;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private final HashMap<String, String> bodyFields;

    public HttpRequestBody(HashMap<String, String> bodyFields) {
        this.bodyFields = bodyFields;
    }

    public Map<String, String> getBodyFields() {
        return Collections.unmodifiableMap(bodyFields);
    }
}
