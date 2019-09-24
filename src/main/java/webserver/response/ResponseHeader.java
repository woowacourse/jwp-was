package webserver.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private Map<String, String> attributes;

    public ResponseHeader() {
        this.attributes = new HashMap<>();
    }

    public boolean addAttribute(String key, String value) {
        if (attributes.containsKey(key)) {
            return false;
        }
        attributes.put(key, value);
        return true;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
