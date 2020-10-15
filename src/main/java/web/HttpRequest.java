package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequest {

    private RequestHeader requestHeader;
    private RequestBody requestBody;

    private HttpRequest(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        RequestHeader requestHeader = RequestHeader.from(br);
        RequestBody requestBody = null;
        if (HttpMethod.POST == requestHeader.getMethod()) {
            requestBody = RequestBody.of(br, requestHeader.getValue("Content-Length"));
        }
        return new HttpRequest(requestHeader, requestBody);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getPath() {
        return requestHeader.getPath();
    }

    public HttpMethod getMethod() {
        return requestHeader.getMethod();
    }

    public Map<String, String> getHeaders() {
        return requestHeader.getParams();
    }
}
