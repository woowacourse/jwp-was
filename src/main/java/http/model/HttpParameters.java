package http.model;

import java.util.Map;
import java.util.Objects;

public class HttpParameters {
    private Map<String, String> parameters;

    public HttpParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void addParameter(String key, String value) {
        parameters.put(key,value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpParameters that = (HttpParameters) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
