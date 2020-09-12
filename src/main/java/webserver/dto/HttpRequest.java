package webserver.dto;

import java.util.Map;
import webserver.Kind;

public class HttpRequest {

    private String httpMethod;
    private String urlPath;
    private String protocol;
    private Map<String, String> headers;
    private Kind kind;

    public HttpRequest(String HttpMethod, String urlPath, String protocol,
        Map<String, String> headers, Kind kind) {
        this.httpMethod = HttpMethod;
        this.urlPath = urlPath;
        this.protocol = protocol;
        this.headers = headers;
        this.kind = kind;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Kind getKind() {
        return kind;
    }

    public boolean isSameKind(Kind kind) {
        return this.kind.equals(kind);
    }
}
