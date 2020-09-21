package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import utils.IOUtils;

public class ServletRequest {
    private final HttpStartLine httpStartLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public ServletRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        this.httpStartLine = HttpStartLine.of(line);
        String newLine = br.readLine();
        StringBuilder headerLines = new StringBuilder();
        while (Objects.nonNull(newLine) && !newLine.isEmpty()) {
            headerLines.append(newLine).append(System.lineSeparator());
            newLine = br.readLine();
        }
        this.requestHeader = RequestHeader.of(headerLines.toString());
        this.requestBody = RequestBody.of(IOUtils.readData(br, requestHeader.getContentLength()));
    }

    public ServletRequest(String startLine, String headerLines, String bodyLines) {
        this.httpStartLine = HttpStartLine.of(startLine);
        this.requestHeader = RequestHeader.of(headerLines);
        this.requestBody = RequestBody.of(bodyLines);
    }

    public Map<String, String> getBody() {
        return requestBody.getAttributes();
    }

    public String getAttribute(String key) {
        return requestBody.getAttribute(key);
    }

    public MethodType getMethod() {
        return httpStartLine.getMethod();
    }

    public String getPath() {
        return httpStartLine.getPath();
    }

    public String getProtocolVersion() {
        return httpStartLine.getProtocolVersion();
    }

    public Map<String, String> getQueryParams() {
        return httpStartLine.getQueryParams();
    }

    public String getQueryParam(String key) {
        return httpStartLine.getParam(key);
    }

    public Map<String, String> getHeaders() {
        return requestHeader.getHeaders();
    }

    public String getHeader(String header) {
        return requestHeader.getHeader(header);
    }

    public String getAccept() {
        return requestHeader.getHeader("Accept");
    }

    public boolean hasStaticResource() {
        return getPath().contains(".");
    }
}
