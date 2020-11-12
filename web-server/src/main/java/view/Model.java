package view;

import java.util.HashMap;
import java.util.Map;

public class Model {

    private final Map<String, Object> attributes;

    public Model() {
        this.attributes = new HashMap<>();
    }

    public Model(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addAttributes(String key, Object value) {
        attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
