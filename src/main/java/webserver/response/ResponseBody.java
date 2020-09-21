package webserver.response;

import java.util.Map;

public class ResponseBody {
    private final Map<String, String> attributes;

    public ResponseBody(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getAttribute(String key) {
        return attributes.getOrDefault(key, null);
    }
}
