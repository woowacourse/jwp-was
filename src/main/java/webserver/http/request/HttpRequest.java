package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        RequestLine requestLine = RequestLine.of(bufferedReader);
        RequestHeader requestHeader = RequestHeader.of(bufferedReader);
        RequestBody requestBody = RequestBody.of(bufferedReader, requestHeader.getContentLength());

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    public String getDefaultPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return requestLine.getParameters();
    }

    public String getBody() {
        return requestBody.getBody();
    }

    public boolean isGet() {
        return requestLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestLine.getMethod() == Method.POST;
    }

    public boolean isPut() {
        return requestLine.getMethod() == Method.PUT;
    }

    public boolean isDelete() {
        return requestLine.getMethod() == Method.DELETE;
    }

    public String getMethod() {
        return requestLine.getMethod().getMethodName();
    }
}
