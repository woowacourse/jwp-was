package webserver.http.request;

import utils.QueryParser;

import java.util.Collections;
import java.util.Map;

public class RequestBody {
    private Map<String, String> body;

    public RequestBody(String body) {
        this.body = QueryParser.parseRequest(body);
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(body);
    }

    public String getValueBy(String key) {
        return body.get(key);
    }
}
