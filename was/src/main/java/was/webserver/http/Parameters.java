package was.webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parameters {
    private static final String CONTENT_DELIMITER = "&";
    private static final String DATA_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> parameters;
    private final ParametersState parametersState;

    private Parameters(Map<String, String> parameters, ParametersState parametersState) {
        this.parameters = parameters;
        this.parametersState = parametersState;
    }

    public static Parameters notEmptyQueryParameters(String parameter) {
        Map<String, String> parameters = initQueryParameters(parameter);
        return new Parameters(parameters, ParametersState.NOT_EMPTY);
    }

    private static Map<String, String> initQueryParameters(String parameter) {
        Map<String, String> parameters = new HashMap<>();
        String[] contents = parameter.split(CONTENT_DELIMITER);
        for (String content : contents) {
            String[] data = content.split(DATA_DELIMITER);
            parameters.put(data[KEY_INDEX], data[VALUE_INDEX]);
        }
        return parameters;
    }

    public static Parameters emptyQueryParameters() {
        return new Parameters(Collections.emptyMap(), ParametersState.EMPTY);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public ParametersState getQueryParametersState() {
        return parametersState;
    }
}
