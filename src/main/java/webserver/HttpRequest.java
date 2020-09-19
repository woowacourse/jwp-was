package webserver;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final String resourcePath;
    private final Map<String, String> parameters;

    public HttpRequest(HttpMethod httpMethod, String resourcePath, Map<String, String> parameters) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.parameters = parameters;
    }

    public HttpRequest(HttpMethod httpMethod, String resourcePath) {
        this(httpMethod, resourcePath, new HashMap<>());
    }

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String[] requestLine = bufferedReader.readLine().split(" ");
        this.httpMethod = HttpMethod.valueOf(requestLine[0]);
        this.resourcePath = parseResourcePath(requestLine[1]);
        this.parameters = parseParameters(requestLine[1]);
    }

    public HttpRequest(InputStream inputStream) throws IOException {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    private String parseResourcePath(String line) {
        return line.split("\\?")[0];
    }

    private Map<String, String> parseParameters(String line) throws UnsupportedEncodingException {
        String resourcePath = parseResourcePath(line);
        if (resourcePath.equals(line)) {
            return new HashMap<>();
        }

        line = line.substring(resourcePath.length() + 1);
        line = URLDecoder.decode(line, "UTF-8");
        String[] queryStrings = line.split("&");

        Map<String, String> parameters = new HashMap<>();
        for (String queryString : queryStrings) {
            parameters.put(queryString.split("=")[0],
                    queryString.split("=")[1]);
        }
        return parameters;
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
}
