package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        RequestLine requestLine = RequestLine.from(br.readLine());
        RequestHeader requestHeader = RequestHeader.from(br);
        RequestBody requestBody = null;
        if (requestLine.isPost()) {
            requestBody = RequestBody.of(br, requestHeader.getValue("Content-Length"), requestLine);
        }
        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeaders(String key) {
        return requestHeader.getParams().get(key);
    }
}
