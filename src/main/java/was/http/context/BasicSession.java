package was.http.context;

import java.util.HashMap;
import java.util.Map;

public class BasicSession implements Session {
    private final Map<String, Object> map = new HashMap<>();

    @Override
    public void add(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public Object get(String key) {
        return map.get(key);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
