package http.request;

import java.util.HashMap;
import java.util.Map;

public class Params {
    private static final String PARAM_BUNDLE_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int SPLIT_SIZE = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    public static final String DEFAULT_VALUE = "";

    private Map<String, String> params;

    private Params(Map<String, String> params) {
        this.params = params;
    }

    public static Params empty() {
        return new Params(new HashMap<>());
    }

    public static Params from(String paramBundle) {
        Map<String, String> params = new HashMap<>();
        String[] splitted = paramBundle.split(PARAM_BUNDLE_DELIMITER);
        for (String param : splitted) {
            String[] keyValues = param.split(KEY_VALUE_DELIMITER, SPLIT_SIZE);
            params.put(keyValues[KEY_INDEX], keyValues[VALUE_INDEX]);
        }
        return new Params(params);
    }

    public String findValueBy(String key) {
        return params.getOrDefault(key, DEFAULT_VALUE);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
