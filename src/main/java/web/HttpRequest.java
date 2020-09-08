package web;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private RequestUri requestUri;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(final BufferedReader bufferedReader) throws IOException {
        this.requestUri = new RequestUri(bufferedReader);
        this.requestHeader = new RequestHeader(bufferedReader);
        this.requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());
    }

    public String getRequestPath() {
        return requestUri.getPath().getRequestPath();
    }

    public String getParam(final String key) {
        return requestUri.getParam(key);
    }

    public int getContentLength() {
        return requestHeader.getContentLength();
    }

    public String getRequestBody() {
        return requestBody.getBody();
    }
}
