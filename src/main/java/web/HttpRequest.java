package web;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestUri requestUri;
    private final RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(final BufferedReader bufferedReader) throws IOException {
        this.requestUri = new RequestUri(bufferedReader.readLine());
        this.requestHeader = new RequestHeader(IOUtils.readHeader(bufferedReader));
        if (HttpMethod.POST == getMethod()) {
            this.requestBody = new RequestBody(IOUtils.readData(bufferedReader, requestHeader.getContentLength()));
        }
    }

    public String getRequestPath() {
        return requestUri.getRequestPath();
    }

    public String getParam(final String key) {
        return requestUri.getParam(key);
    }

    public int getContentLength() {
        return requestHeader.getContentLength();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpMethod getMethod() {
        return requestUri.getMethod();
    }

    public boolean isGetMethod() {
        return getMethod().isGet();
    }
}
