package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
    private final Map<String, String> parameters = new HashMap<>();

    Parameters() {
    }

    void add(final String key, final String value) {
        parameters.put(key, value);
    }

    void addAll(final Map<String, String> map) {
        parameters.putAll(map);
    }

    String getParameter(final String key) {
        return parameters.get(key);
    }

    int size() {
        return parameters.size();
    }
}
