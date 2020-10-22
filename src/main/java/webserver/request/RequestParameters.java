package webserver.request;

import java.util.Map;
import java.util.Set;

public class RequestParameters {
    private final Map<String, String> parameters;

    public RequestParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getValue(String key) {
        return parameters.get(key);
    }

    public Set<String> getKeys() {
        return parameters.keySet();
    }


}
