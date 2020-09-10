package http;

import java.io.BufferedReader;
import java.io.IOException;

import utils.IOUtils;

public class Request {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public Request(BufferedReader br) throws IOException {
        this.requestLine = IOUtils.ofRequestLine(br.readLine());
        this.requestHeader = IOUtils.ofRequestHeader(br);
        this.requestBody = IOUtils.ofRequestBody(br);
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
