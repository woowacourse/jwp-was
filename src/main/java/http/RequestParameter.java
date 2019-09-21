package http;

import java.util.Map;

public class RequestParameter {
    Map<String, String> parameters;

    public RequestParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
