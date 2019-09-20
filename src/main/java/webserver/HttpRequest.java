package webserver;

import webserver.httpRequest.HttpRequestBody;
import webserver.httpRequest.HttpRequestHeader;
import webserver.httpRequest.HttpStartLine;

public class HttpRequest {
    private HttpStartLine httpStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public String getMethod() {
        return httpStartLine.getMethod();
    }

    public String getPath() {
        return httpStartLine.getPath();
    }

    public String getParam(String key) {
        String bodyParam = httpRequestBody.getBodyParam(key);
        if (bodyParam == null) {
            String param = httpStartLine.getParam(key);
            if (param == null) {
                return "";
            }
        }
        return bodyParam;
    }


}
