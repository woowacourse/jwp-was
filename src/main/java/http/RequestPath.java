package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestPath {
    private final String path;
    private Map<String, String> parameters;

    public RequestPath(String path) {
        String[] tokens = path.split("/");
        if (path.contains("?")) {
            String[] lastTokens = tokens[tokens.length - 1].split("\\?");
            String[] params = lastTokens[lastTokens.length - 1].split("&");
            parameters = extractParameter(params);
        }
        this.path = "../resources/templates" + path;
    }

    private Map<String, String> extractParameter(String[] params) {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(params)
                .forEach(param -> {
                    String[] datas = param.split("=");
                    map.put(datas[0], datas[1]);
                });

        return map;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
