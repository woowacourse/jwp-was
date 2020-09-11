package web;

import java.util.HashMap;
import java.util.Map;

public class RequestPath {
    private final String fullPath;
    private final String requestPath;
    private final Map<String, String> requestParameters = new HashMap<>();

    public RequestPath(String requestPath) {
        this.fullPath = requestPath;
        String[] splitPath = requestPath.split("\\?");
        this.requestPath = splitPath[0];
        if (hasNoParameters()) {
            return;
        }
        String parameters = splitPath[1];
        String[] splitParameters = parameters.split("&");
        for (String parameter : splitParameters) {
            String[] key = parameter.split("=");
            requestParameters.put(key[0], key[1]);
        }
    }

    private boolean hasNoParameters() {
        return this.fullPath.equals(this.requestPath);
    }

    public String getRequestPath() {
        return this.requestPath;
    }

    public String getRequestParameter(String key) {
        return requestParameters.get(key);
    }
}
