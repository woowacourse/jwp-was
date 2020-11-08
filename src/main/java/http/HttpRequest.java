package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br);
        this.requestHeader = new RequestHeader(br);
        this.requestBody = new RequestBody(br, requestHeader.getContentLength());
    }

    public boolean isStaticFileRequest() {
        return this.requestLine.isStaticFile();
    }

    public String getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public Map<String, String> getFormData() {
        return this.requestBody.getFormData();
    }

    public Integer getContentLength() {
        return this.requestHeader.getContentLength();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
