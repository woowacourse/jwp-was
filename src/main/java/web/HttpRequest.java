package web;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestHeader = new RequestHeader(br);
        if (requestHeader.isPost()) {
            this.requestBody = new RequestBody(br);
        } else {
            this.requestBody = null;
        }
    }

    public boolean isPost() {
        return requestHeader.isPost();
    }

    public String getRequestUri() {
        return requestHeader.getRequestUri().getUri();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
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
