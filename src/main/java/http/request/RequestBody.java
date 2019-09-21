package http.request;

import java.util.Map;

public class RequestBody {

    private Map<String, String> body;

    public RequestBody(final Map<String, String> body) {
        this.body = body;
    }

    public String findParam(String name) {
        return body.get(name);
    }
}
