package web;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        int contentLength = requestHeader.getContentLength();
        String body = IOUtils.readData(br, contentLength);
        this.requestBody = new RequestBody(body);
    }

    public boolean isStaticFileRequest() {
        return this.requestLine.isStaticFile();
    }

    public String getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public Map<String, String> getFormData() {
        return this.requestBody.getFormData();
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
