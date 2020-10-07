package http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestPath {

    private static final String PATH_PARAM_DELIMITER = "\\?";
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";

    private final String path;
    private final Map<String, String> parameters;

    private RequestPath(String path) {
        this.path = path;
        this.parameters = new HashMap<>();
    }

    private RequestPath(String path, Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static RequestPath from(String fullPath) {
        String[] splitPath = fullPath.split(PATH_PARAM_DELIMITER);
        String path = splitPath[0];
        if (fullPath.equals(path)) {
            return new RequestPath(path);
        }

        Map<String, String> parameters = new HashMap<>();
        String params = splitPath[1];
        String[] splitParams = params.split(PARAM_DELIMITER);
        for (String param : splitParams) {
            String[] keyValue = param.split(PARAM_KEY_VALUE_DELIMITER);
            parameters.put(keyValue[0], keyValue[1]);
        }
        return new RequestPath(path, parameters);
    }

    public String getPath() {
        return path;
    }

    public String getParameters(String key) {
        return parameters.get(key);
    }
}
