package web;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        this.requestLine = new RequestLine(firstLine);

        List<String> requestHeaders = new ArrayList<>();
        String request;
        while (!(request = br.readLine()).equals("")) {
            requestHeaders.add(request);
        }
        this.requestHeader = new RequestHeader(requestHeaders);

        final int contentLength = requestHeader.getContentLength();
        final String body = IOUtils.readData(br, contentLength);
        this.requestBody = new RequestBody(body);
    }

    public boolean isStaticFileRequest() {
        return this.requestLine.isStaticFile();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getMethod() {
        return this.requestLine.getMethod();
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
