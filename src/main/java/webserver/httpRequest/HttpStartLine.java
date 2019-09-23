package webserver.httpRequest;

import webserver.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpStartLine {
    private static final String START_LINE_SEPARATOR = " ";
    private static final String QUERY_SEPARATOR = "\\?";
    private static final String QUERY_PARAMETER_SEPARATOR = "=";

    private final HttpMethod method;
    private final String path;
    private final Map<String, String> parameters;
    private final String httpVersion;

    private HttpStartLine(HttpMethod method, String path, Map<String, String> parameters, String httpVersion) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.httpVersion = httpVersion;
    }

    public static HttpStartLine of(String startLine) {
        String[] splitStartLine = startLine.split(START_LINE_SEPARATOR);

        Map<String, String> queryParams = new HashMap<>();

        HttpMethod httpMethod = HttpMethod.valueOf(splitStartLine[0]);
        String target = splitStartLine[1];
        String[] splitTarget = target.split(QUERY_SEPARATOR);
        String path = splitTarget[0];

        if (hasQueryParams(splitTarget)) {
            parseQueryParams(queryParams, splitTarget);
        }
        return new HttpStartLine(httpMethod, path, queryParams, splitStartLine[2]);
    }

    private static boolean hasQueryParams(String[] splitTarget) {
        return splitTarget.length == 2;
    }

    private static void parseQueryParams(Map<String, String> queryParams, String[] splitTarget) {
        for (String pair : splitTarget) {
            String[] split1 = pair.split(QUERY_PARAMETER_SEPARATOR);
            String key = split1[0];
            String value = "";
            if (split1.length == 2) {
                value = split1[1];
            }
            queryParams.put(key, value);
        }
    }

    public boolean checkMethod(HttpMethod httpMethod) {
        return method.equals(httpMethod);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getParam(String key) {
        return parameters.get(key);
    }

    public String getPath() {
        return path;
    }
}
