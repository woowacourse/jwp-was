package webserver.http.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeader {
    private final HttpMethod httpMethod;
    private final String resourcePath;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;

    public HttpRequestHeader(HttpMethod httpMethod, String resourcePath,
                             Map<String, String> headers,
                             Map<String, String> parameters) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.headers = headers;
        this.parameters = parameters;
    }

    public HttpRequestHeader(HttpMethod httpMethod, String resourcePath) {
        this(httpMethod, resourcePath, new HashMap<>(), new HashMap<>());
    }

    public HttpRequestHeader(List<String> lines) throws IOException {
        String[] splited = lines.get(0).split(" ");
        this.httpMethod = HttpMethod.valueOf(splited[0]);
        this.resourcePath = parseResourcePath(splited[1]);
        this.parameters = parseParameters(splited[1]);

        this.headers = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            splited = lines.get(i).split(": ");
            if (splited.length >= 2) {
                headers.put(splited[0], splited[1]);
            }
        }
    }

    private String parseResourcePath(String line) {
        return line.split("\\?")[0];
    }

    private Map<String, String> parseParameters(String line) throws UnsupportedEncodingException {
        String resourcePath = parseResourcePath(line);
        if (resourcePath.equals(line)) {
            return new HashMap<>();
        }
        return ParameterParser.parse(line.substring(resourcePath.length() + 1));
    }

    public boolean isResourcePath(String resourcePath) {
        return this.resourcePath.equals(resourcePath);
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

    public Map<String, String> getHeaders() {
        return headers;
    }
}
