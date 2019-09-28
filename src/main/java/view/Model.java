package view;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Object> attributes = new HashMap<>();

    public Model() { }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttribute(String key, Object attribute) {
        attributes.put(key, attribute);
    }
}
