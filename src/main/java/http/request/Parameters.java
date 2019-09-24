package http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parameters {
    private Map<String, String> parameters;

    public Parameters(String value) {
        splitValue(value);
    }

    private void splitValue(String value) {
        parameters = new HashMap<>();

        if ("".equals(value)) {
            return;
        }

        String[] tokens = value.split("&");

        Arrays.stream(tokens).forEach(token -> {
            String[] parameterKeyValue = token.split("=");
            parameters.put(parameterKeyValue[0], parameterKeyValue[1]);
        });
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "parameters=" + parameters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return parameters.equals(that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
