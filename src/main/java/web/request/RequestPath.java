package web.request;

import java.util.HashMap;
import java.util.Map;

public class RequestPath {
    private final String target;
    private final Map<String, String> parameters;

    public RequestPath(String path) {
        target = path.split("\\?")[0];
        parameters = new HashMap<>();
        if(path.contains("?")) {
            mappingParameters(path.split("\\?")[1]);
        }
    }

    private void mappingParameters(String pathParams) {
        for(String parameter : pathParams.split("&")) {
            String[] tokens = parameter.split("=");
            String key = tokens[0];
            String value = tokens[1];

            parameters.put(key, value);
        }
    }

    public String getTarget() {
        return target;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
