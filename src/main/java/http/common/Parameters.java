package http.common;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
    private Map<String, String> parameters = new HashMap<>();

    public void addAll(Map<String, String> params) {
        parameters.putAll(params);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
