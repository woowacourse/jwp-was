package webserver.controller.request.header;

import exception.HttpMethodNotFoundException;
import webserver.controller.request.MimeType;

public class HttpRequestLine {
    private HttpMethod httpMethod;
    private String url;
    private String version;

    public HttpRequestLine(String[] requestLine) throws HttpMethodNotFoundException {
        this.httpMethod = HttpMethod.match(requestLine[0]);
        this.url = requestLine[1];
        this.version = requestLine[2];
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public MimeType getMimeType() {
        return MimeType.match(url);
    }
}
