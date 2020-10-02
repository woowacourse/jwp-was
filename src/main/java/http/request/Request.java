package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import http.HttpHeader;
import utils.IOUtils;

public class Request {
    private final static String DELIMITER = ": ";

    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final RequestBody requestBody;

    public Request(BufferedReader br) throws Exception {
        this.requestLine = new RequestLine(br);
        this.httpHeader = new HttpHeader(ofRequestHeader(br));
        this.requestBody = new RequestBody(br, httpHeader.getContentLength());
        IOUtils.printRequest(this);
    }

    private Map<String, String> ofRequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        Map<String, String> requestHeaders = new HashMap<>();
        while (line != null && !"".equals(line)) {
            String[] token = line.split(DELIMITER);
            requestHeaders.put(token[0], token[1]);
            line = br.readLine();
        }

        return requestHeaders;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getRequestHeader() {
        return httpHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public boolean isMethod(RequestMethod requestMethod) {
        return requestMethod == requestLine.getMethod();
    }
}
