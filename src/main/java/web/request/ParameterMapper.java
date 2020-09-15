package web.request;

import exception.InvalidRequestParamsException;

import java.util.HashMap;
import java.util.Map;

public abstract class ParameterMapper {
    private final Map<String, String> parameters;

    public ParameterMapper() {
        this.parameters = new HashMap<>();
    }

    protected void mappingParameters(String params) {
        for (String parameter : params.split("&")) {
            validateParameter(parameter);
            String[] tokens = parameter.split("=");
            String key = tokens[0];
            String value = tokens[1];

            parameters.put(key, value);
        }
    }

    private void validateParameter(String parameter) {
        String key = parameter.split("=")[0];
        if (!parameter.contains("=") || key.isEmpty()) {
            throw new InvalidRequestParamsException();
        }
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
