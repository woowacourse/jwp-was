package http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br);
        this.requestHeader = new RequestHeader(br);
        this.requestBody = new RequestBody(br, this.requestHeader.getContentLength());
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public boolean isMethodEqualTo(RequestMethod requestMethod) {
        return this.requestLine.isMethodEqualTo(requestMethod);
    }

    public String getBodyValue(String key) {
        return this.requestBody.getValue(key);
    }

    public boolean headerContainsValueOf(HeaderParam key, String value) {
        return this.requestHeader.containsValueOf(key, value);
    }
}
