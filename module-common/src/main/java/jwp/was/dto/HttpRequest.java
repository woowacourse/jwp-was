package jwp.was.dto;

import java.util.Map;
import jwp.was.util.HttpMethod;

public class HttpRequest {

    private final HttpMethod httpMethod;
    private final UrlPath urlPath;
    private final Parameters parameters;
    private final HttpVersion httpVersion;
    private final Headers headers;

    public HttpRequest(HttpMethod httpMethod, UrlPath urlPath, Parameters parameters,
        HttpVersion httpVersion, Headers headers) {
        this.httpMethod = httpMethod;
        this.urlPath = urlPath;
        this.parameters = parameters;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getUrlPath() {
        return urlPath.getUrlPath();
    }

    public Map<String, String> getParameters() {
        return parameters.getParameters();
    }

    public String getHttpVersion() {
        return httpVersion.getHttpVersion();
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public boolean isFile() {
        return urlPath.isFile();
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public String getParameter(String parameter) {
        return parameters.getParameter(parameter);
    }
}
