package web;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestHeader = new RequestHeader(br);
        this.requestBody = new RequestBody(br);
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }
}
