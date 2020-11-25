package server.web.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Model {
    private final Map<String, Object> attributes = new LinkedHashMap<>();

    public void setAttribute(String name, Object data) {
        this.attributes.put(name, data);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
