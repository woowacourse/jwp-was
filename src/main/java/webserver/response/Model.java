package webserver.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class Model {
    private final Map<String, String> attributes;

    public Model(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static Model emptyModel() {
        return new Model(new LinkedHashMap<>());
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getAttribute(String key) {
        return attributes.getOrDefault(key, null);
    }

    public void addBody(String key, String value) {
        attributes.put(key, value);
    }
}
