package http;

import java.util.Map;

public class HttpBody {
    private final Map<String, String> httpBody;

    public HttpBody(Map<String, String> httpBody) {
        this.httpBody = httpBody;
    }

    public String getValue(String key) {
        return httpBody.get(key);
    }
}
