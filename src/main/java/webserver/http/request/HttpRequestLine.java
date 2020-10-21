package webserver.http.request;

import webserver.http.Protocol;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestLine {
    private final HttpMethod httpMethod;
    private final String resourcePath;
    private final Map<String, String> parameters;
    private final Protocol protocol;

    public HttpRequestLine(HttpMethod httpMethod, String resourcePath, Map<String, String> parameters, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.parameters = parameters;
        this.protocol = protocol;
    }

    public HttpRequestLine(HttpMethod httpMethod, String resourcePath) {
        this(httpMethod, resourcePath, new HashMap<>(), Protocol.getDefaultProtocol());
    }

    public HttpRequestLine(String line) throws UnsupportedEncodingException {
        String[] splited = line.split(" ");
        this.httpMethod = HttpMethod.valueOf(splited[0]);
        this.resourcePath = parseResourcePath(splited[1]);
        this.parameters = parseParameters(splited[1]);
        this.protocol = Protocol.find(splited[2]);
    }

    private String parseResourcePath(String line) {
        return line.split("\\?")[0];
    }

    private Map<String, String> parseParameters(String line) throws UnsupportedEncodingException {
        String resourcePath = parseResourcePath(line);
        if (resourcePath.equals(line)) {
            return new HashMap<>();
        }
        return Parser.parseParameter(line.substring(resourcePath.length() + 1));
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
