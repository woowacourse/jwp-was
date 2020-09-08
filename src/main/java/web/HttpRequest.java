package web;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private RequestUri requestUri;
    private RequestHeader requestHeader;

    public HttpRequest(final BufferedReader bufferedReader) throws IOException {
        this.requestUri = new RequestUri(bufferedReader);
        this.requestHeader = new RequestHeader(bufferedReader);
    }

    public String getRequestPath() {
        return requestUri.getPath().getRequestPath();
    }

    public String getParam(final String key) {
        return requestUri.getParam(key);
    }
}
