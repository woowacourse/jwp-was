package web.request;

import exception.InvalidRequestPathException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestPath {
    private final String target;
    private final Map<String, String> pathParameters;

    public RequestPath(String path) {
        target = path.split("\\?")[0];
        pathParameters = new HashMap<>();
        if(path.contains("?")) {
            mappingPathParameters(path.split("\\?")[1]);
        }
    }

    private void mappingPathParameters(String pathParams) {
        for(String parameter : pathParams.split("&")) {
            validateParameter(parameter);
            String[] tokens = parameter.split("=");
            String key = tokens[0];
            String value = tokens[1];

            pathParameters.put(key, value);
        }
    }

    private void validateParameter(String parameter) {
        String key = parameter.split("=")[0];
        if(!parameter.contains("=") || key.isEmpty()) {
            throw new InvalidRequestPathException();
        }
    }

    public String getTarget() {
        return target;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }
}
