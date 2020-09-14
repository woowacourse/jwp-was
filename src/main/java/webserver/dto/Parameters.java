package webserver.dto;

import java.util.Collections;
import java.util.Map;

public class Parameters {

    private final Map<String, String> parameters;

    public Parameters(Map<String, String> parameters) {
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static Parameters from(Map<String, String> parameters) {
        return new Parameters(parameters);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
