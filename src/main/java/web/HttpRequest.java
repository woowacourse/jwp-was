package web;

import java.io.BufferedReader;
import java.io.IOException;

import utils.IOUtils;

public class HttpRequest {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestHeader = new RequestHeader(br);
        if (requestHeader.isPost()) {
            String data = IOUtils.readData(br, requestHeader.getContentLength());
            this.requestBody = new RequestBody(data);
        } else {
            this.requestBody = null;
        }
    }

    public boolean isPost() {
        return requestHeader.isPost();
    }

    public boolean isStaticFile() {
        return requestHeader.isStaticFile();
    }

    public RequestUri getRequestUri() {
        return requestHeader.getRequestUri();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestHeader=" + requestHeader +
                ", requestBody=" + requestBody +
                '}';
    }
}
