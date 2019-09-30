package utils.modelandview.model;

import java.util.HashMap;
import java.util.Map;

public class HandleBarModel implements Model {
    private Map<String, Object> data;

    public HandleBarModel() {
        data = new HashMap<>();
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public void putData(String name, Object o) {
        data.put(name, o);
    }
}
