package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private HttpStartLine httpStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public static HttpRequest of(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        HttpStartLine startLine = HttpStartLine.of(br);
        HttpRequestHeader requestHeader = HttpRequestHeader.of(br);
        HttpRequestBody requestBody = HttpRequestBody.of(br, requestHeader.getContentLength());

        return new HttpRequest(startLine, requestHeader, requestBody);
    }

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
    }

    public String getParam(String key) {
        return httpRequestBody.get(key);
    }
}
