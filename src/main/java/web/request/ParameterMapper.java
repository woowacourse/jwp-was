package web.request;

import exception.InvalidRequestParamsException;
import exception.RequestParameterNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ParameterMapper {
    private final Map<String, String> parameters;

    public ParameterMapper() {
        this.parameters = new HashMap<>();
    }

    protected void mappingParameters(String params) {
        validateParameters(params);
        for (String parameter : params.split("&")) {
            validateParameter(parameter);
            String[] tokens = parameter.split("=");
            String key = tokens[0];
            String value = tokens[1];

            parameters.put(key, value);
        }
    }

    private void validateParameters(String params) {
        if (Objects.isNull(params) || params.isEmpty() || !params.contains("=")) {
            throw new InvalidRequestParamsException();
        }
    }

    private void validateParameter(String parameter) {
        if(parameter.isEmpty() || !parameter.contains("=") || parameter.equals("=")) {
            throw new InvalidRequestParamsException();
        }
        String key = parameter.split("=")[0];
        if (!parameter.contains("=") || key.isEmpty()) {
            throw new InvalidRequestParamsException();
        }
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameterByKey(String key) {
        if(Objects.isNull(key) || key.isEmpty()) {
            throw new RequestParameterNotFoundException();
        }
        if(!parameters.containsKey(key)) {
            throw new RequestParameterNotFoundException(key);
        }
        return parameters.get(key);
    }
}
