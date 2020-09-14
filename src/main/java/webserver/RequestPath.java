package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestPath {

    private static final String PATH_PARAM_DELIMITER = "\\?";
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";

    private String path;
    private Map<String, String> parameters = new HashMap<>();

    public RequestPath(String path) {
        String[] splitPath = path.split(PATH_PARAM_DELIMITER);
        this.path = splitPath[0];
        if (path.equals(this.path)) {
            return;
        }
        String params = splitPath[1];
        String[] splitParams = params.split(PARAM_DELIMITER);
        for (String param : splitParams) {
            String[] keyValue = param.split(PARAM_KEY_VALUE_DELIMITER);
            parameters.put(keyValue[0], keyValue[1]);
        }
    }

    public String getPath() {
        return path;
    }

    public String getParameters(String key) {
        return parameters.get(key);
    }
}
