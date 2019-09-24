package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private final String method;
    private final String target;
    private final String httpVersion;
    private final Map<String, String> parameters;

    private RequestLine(String method, String target, String httpVersion, Map<String, String> parameters) {
        this.method = method;
        this.target = target;
        this.httpVersion = httpVersion;
        this.parameters = parameters;
    }

    public static RequestLine of(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] splitRequestLine = requestLine.split(" ");
        String method = splitRequestLine[0];
        String target = splitRequestLine[1];
        String httpVersion = splitRequestLine[2];

        return new RequestLine(method, target, httpVersion, getParameters(target));
    }

    private static Map<String, String> getParameters(String target) {
        if (hasNotParameters(target)) {
            return Collections.emptyMap();
        }

        Map<String, String> parameters = new HashMap<>();
        String[] split = target.split("\\?")[1].split("&");
        for (String pair : split) {
            addAttribute(parameters, pair);
        }
        return parameters;
    }

    private static boolean hasNotParameters(String target) {
        return target.split("\\?").length == 2;
    }

    private static void addAttribute(Map<String, String> parameters, String pair) {
        String[] split1 = pair.split("=");
        String key = split1[0];
        String value = determineValue(split1);
        parameters.put(key, value);
    }

    private static String determineValue(String[] split1) {
        String value = "";
        if (split1.length == 2) {
            value = split1[1];
        }
        return value;
    }

    public boolean isGet() {
        return "GET".equals(method);
    }

    public boolean isPost() {
        return "POST".equals(method);
    }

    public boolean isPut() {
        return "PUT".equals(method);
    }

    public boolean isDelete() {
        return "DELETE".equals(method);
    }

    public boolean hasParameters() {
        return target.split("\\?").length == 2;
    }


    public String getPath() {
        return target.split("\\?")[0];
    }

    public String getContentType() {
        System.out.println("getPath() = " + getPath());
        String[] split = getPath().split("\\.");
        int length = split.length;
        return split[length - 1];
    }

    public String getMethod() {
        return method;
    }

    public String getTarget() {
        return target;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String get(String attributeName) {
        return parameters.get(attributeName);
    }
}
