package webserver.httpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpStartLine {
    private final String method;
    private final String path;
    private final Map<String, String> parameters;
    private final String httpVersion;

    private HttpStartLine(String method, String path, Map<String, String> parameters, String httpVersion) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.httpVersion = httpVersion;
    }

    public static HttpStartLine of(String startLine) {
        String[] splitStartLine = startLine.split(" ");

        Map<String, String> queryParams = new HashMap<>();

        String target = splitStartLine[1];
        String[] splitTarget = target.split("\\?");
        String path = splitTarget[0];

        if (splitTarget.length == 2) {
            parseQueryParams(queryParams, splitTarget);
        }
        return new HttpStartLine(splitStartLine[0], path, queryParams, splitStartLine[2]);
    }

    private static void parseQueryParams(Map<String, String> queryParams, String[] splitTarget) {
        for (String pair : splitTarget) {
            String[] split1 = pair.split("=");
            String key = split1[0];
            String value = "";
            if (split1.length == 2) {
                value = split1[1];
            }
            queryParams.put(key, value);
        }
    }

    public boolean isGet() {
        return method.equals("GET");
    }

    public boolean isPost() {
        return method.equals("POST");
    }

    public String getMethod() {
        return method;
    }

    public String getParam(String key) {
        return parameters.get(key);
    }

    public String getPath() {
        return path;
    }
}
