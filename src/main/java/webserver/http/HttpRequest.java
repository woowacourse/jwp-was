package webserver.http;

import webserver.http.httpRequest.HttpRequestBody;
import webserver.http.httpRequest.HttpRequestHeader;
import webserver.http.httpRequest.HttpStartLine;

public class HttpRequest {
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSESSION_ID = "JSESSIONID";

    private HttpStartLine httpStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public boolean checkMethod(HttpMethod httpMethod) {
        return httpStartLine.checkMethod(httpMethod);
    }

    public String getPath() {
        return httpStartLine.getPath();
    }

    public String getParam(String key) {
        String param = httpRequestBody.getBodyParam(key);
        if (param == null) {
            param = httpStartLine.getParam(key);
        }
        return param;
    }

    public String getCookie() {
        return httpRequestHeader.getCookie();
    }

    public String getSessionId() {
        return httpRequestHeader.getSessionId();
    }
}
