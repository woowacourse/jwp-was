package http;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> requestBodies = new HashMap<>();

    public void put(String key, String value){
        requestBodies.put(key, value);
    }

    public Map<String, String> getRequestBodies() {
        return requestBodies;
    }
}
