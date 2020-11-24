package webserver.model;

import java.util.Map;

public class Model {
    private final Map<String, Object> attributes;

    public Model(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
