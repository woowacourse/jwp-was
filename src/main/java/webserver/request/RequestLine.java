package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        String target = URLDecoder.decode(splitRequestLine[1], "UTF-8");
        String httpVersion = splitRequestLine[2];

        return new RequestLine(method, target, httpVersion, getParameters(target));
    }

    private static Map<String, String> getParameters(String target) {
        if (hasNotParameters(target)) {
            return Collections.emptyMap();
        }

        Map<String, String> parameters = new HashMap<>();
        String[] queryString = target.split("\\?")[1].split("&");
        for (String pair : queryString) {
            addAttribute(parameters, pair);
        }
        return parameters;
    }

    private static boolean hasNotParameters(String target) {
        return target.split("\\?").length != 2;
    }

    private static void addAttribute(Map<String, String> parameters, String pair) {
        String[] split1 = pair.split("=");
        String key = split1[0];
        String value = determineValue(split1);
        parameters.put(key, value);
    }

    private static String determineValue(String[] split1) {
        if (split1.length == 2) {
            return split1[1];
        }
        return "";
    }

    public boolean isGet() {
        return "GET".equals(method);
    }

    public boolean isPost() {
        return "POST".equals(method);
    }

    public boolean hasParameters() {
        return target.split("\\?").length == 2;
    }


    public String getPath() {
        return target.split("\\?")[0];
    }

    public String get(String attributeName) {
        return parameters.get(attributeName);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(target, that.target) &&
                Objects.equals(httpVersion, that.httpVersion) &&
                Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, target, httpVersion, parameters);
    }
}
