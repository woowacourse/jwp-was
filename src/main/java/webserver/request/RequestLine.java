package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static webserver.support.ConStants.*;

public class RequestLine {
    private static final String URL_SEPARATOR = "\\?";

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
        String[] splitRequestLine = requestLine.split(STATUS_LINE_SEPARATOR);
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
        String[] attributes = target.split(URL_SEPARATOR)[1].split(PARAMETER_SEPARATOR);
        for (String attribute : attributes) {
            addAttribute(parameters, attribute);
        }
        return parameters;
    }

    private static boolean hasNotParameters(String target) {
        return target.split(URL_SEPARATOR).length != 2;
    }

    private static void addAttribute(Map<String, String> parameters, String attribute) {
        String[] splitAttribute = attribute.split(ATTRIBUTE_SEPARATOR);
        String key = splitAttribute[0];
        String value = determineValue(splitAttribute);
        parameters.put(key, value);
    }

    private static String determineValue(String[] attribute) {
        if (isNotBlankValue(attribute)) {
            return attribute[1];
        }
        return BLANK;
    }

    private static boolean isNotBlankValue(String[] attribute) {
        return attribute.length == 2;
    }

    public boolean isGet() {
        return METHOD_GET.equals(method);
    }

    public boolean isPost() {
        return METHOD_POST.equals(method);
    }

    public boolean hasParameters() {
        return target.split(URL_SEPARATOR).length == 2;
    }


    public String getPath() {
        return target.split(URL_SEPARATOR)[0];
    }

    public String getParameterValue(String parameterName) {
        if (parameters.containsKey(parameterName)) {
            return parameters.get(parameterName);
        }
        throw new IllegalArgumentException("Not Found Body parameter");
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
