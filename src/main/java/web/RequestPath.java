package web;

import java.util.HashMap;
import java.util.Map;

public class RequestPath {

    private String requestPath;
    private Map<String, String> requestParameters = new HashMap<>();

    public RequestPath(String requestPath) {
        this.requestPath = requestPath;
        String parameters = requestPath.split("\\?")[1];
        String[] splittedParameters = parameters.split("&");
        for (String parameter : splittedParameters) {
            String[] key = parameter.split("=");
            requestParameters.put(key[0], key[1]);
        }
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getRequestParameter(String key) {
        return requestParameters.get(key);
    }
}
