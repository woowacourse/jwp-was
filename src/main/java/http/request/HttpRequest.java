package http.request;

public class HttpRequest {

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public String getUri() {
        return httpRequestLine.getUri();
    }

    public String getHeaderByName(String name) {
        return httpRequestHeader.getValue(name);
    }

    public String getBodyByName(String name) {
        return httpRequestBody.getValue(name);
    }
}
