package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

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
        if (HttpMethod.POST == requestLine.getMethod()) {
            requestBody = RequestBody.of(br, requestHeader.getValue("Content-Length"));
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

    public Map<String, String> getHeaders() {
        return requestHeader.getParams();
    }
}
