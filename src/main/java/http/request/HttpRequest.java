package http.request;

import http.HttpMethod;
import http.MediaType;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private QueryParameter queryParameter; //TODO: queryParameter 가 여기 있는게 맞을까?
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeader httpRequestHeader,
                       QueryParameter queryParameter, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.queryParameter = queryParameter;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public boolean isContainExtension() {
        return MediaType.isContain(httpRequestLine.getPath());
    }

    public byte[] getHttpRequestBody() {
        return httpRequestBody.getBody();
    }

    public String getQueryValue(String key) {
        return queryParameter.getValue(key);
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUri() {
        return httpRequestLine.getPath();
    }
}
