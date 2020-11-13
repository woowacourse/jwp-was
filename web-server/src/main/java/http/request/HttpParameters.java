package http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpParameters {

    private Map<String, String> parameters;

    public HttpParameters() {
        parameters = new HashMap<>();
    }

    public HttpParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HttpParameters))
            return false;
        HttpParameters that = (HttpParameters)o;
        return Objects.equals(getParameters(), that.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParameters());
    }
}
