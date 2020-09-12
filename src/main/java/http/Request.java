package http;

import java.io.BufferedReader;

public class Request {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public Request(BufferedReader br) throws Exception {
        this.requestLine = new RequestLine(br);
        this.requestHeader = new RequestHeader(br);
        this.requestBody = new RequestBody(br, requestHeader.getContentLength());
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

    public boolean isMethod(RequestMethod requestMethod) {
        return requestMethod == requestLine.getMethod();
    }
}
