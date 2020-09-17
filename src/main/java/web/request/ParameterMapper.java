package web.request;

import exception.InvalidRequestParamsException;
import exception.RequestParameterNotFoundException;
import jdk.internal.joptsimple.internal.Strings;

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
        System.out.println(params + " hey TWICE");
        for (String parameter : params.split("&")) {
            System.out.println(parameter);
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
        try {
            String key = parameter.split("=")[0];
            String value = parameter.split("=")[1];
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            if(key.isEmpty() || value.isEmpty()) {
                throw new InvalidRequestParamsException();
            }
        } catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
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
