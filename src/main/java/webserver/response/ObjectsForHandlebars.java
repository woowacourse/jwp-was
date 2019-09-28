package webserver.response;

import java.util.HashMap;
import java.util.Map;

public class ObjectsForHandlebars {

    private final Map<String, Object> objects = new HashMap<>();

    public void put(String key, Object value) {
        objects.put(key, value);
    }

    public Map<String, Object> getObjects() {
        return objects;
    }
}
