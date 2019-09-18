package http;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Object> attributes;

    public Model(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Model() {
        attributes = new HashMap<>();
    }

    public void addAttributes(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttributes(String key) {
        return attributes.get(key);
    }
}
