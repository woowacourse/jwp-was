package http;

import static utils.StringUtils.*;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
    private static final String PARAMS_DELIMITER = "&";
    private static final String PARAM_DELIMITER = "=";
    private static final int PARAMETER_NAME_INDEX = 0;
    private static final int PARAMETER_VALUE_INDEX = 1;
    private final Map<String, String> params;

    private Parameters(Map<String, String> params) {
        this.params = params;
    }

    private Parameters() {
        this.params = new HashMap<>();
    }

    public static Parameters parse(String token) {
        Map<String, String> params = new HashMap<>();
        token = deleteLastEmptyLine(token);
        for (String param : token.split(PARAMS_DELIMITER)) {
            String[] paramTokens = param.split(PARAM_DELIMITER);
            params.put(paramTokens[PARAMETER_NAME_INDEX], paramTokens[PARAMETER_VALUE_INDEX]);
        }
        return new Parameters(params);
    }

    public static Parameters parse() {
        return new Parameters();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String get(String param) {
        return params.get(param);
    }
}
