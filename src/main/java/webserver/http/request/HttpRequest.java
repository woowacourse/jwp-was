package webserver.http.request;

import java.util.List;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        if (requestLine.allowBody()) {
            this.requestBody = requestBody;
        }
    }

    public RequestMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getUrl() {
        return getRequestUrl().getUrl();
    }

    public String getParameter(String key) {
        return getRequestParameter().getOneParameterValue(key);
    }

    public List<String> getAllParameter(String key) {
        return getRequestParameter().get(key);
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public RequestUrl getRequestUrl() {
        return requestLine.getHttpUrl();
    }

    public RequestParams getRequestParameter() {
        return getRequestUrl().getHttpRequestParams();
    }

    public String getBody() {
        return requestBody.getBody();
    }
}
