package webserver;

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
        line = br.readLine();
        StringBuilder headerLines = new StringBuilder();
        while (Objects.nonNull(line) && !line.isEmpty()) {
            headerLines.append(line).append("\n");
            line = br.readLine();
        }
        requestHeader = RequestHeader.of(headerLines.toString());
        requestBody = RequestBody.of(IOUtils.readData(br, requestHeader.getContentLength()));
    }

    public ServletRequest(String startLine, String headerLines, String bodyLines) {
        this.httpStartLine = HttpStartLine.of(startLine);
        this.requestHeader = RequestHeader.of(headerLines);
        this.requestBody = RequestBody.of(bodyLines);
    }

    public Map<String, String> getBody() {
        return requestBody.getAttributes();
    }

    public String getPath() {
        return httpStartLine.getPath();
    }

    public MethodType getMethod() {
        return httpStartLine.getMethod();
    }

    public String getProtocolVersion() {
        return httpStartLine.getProtocolVersion();
    }

    public Map<String, String> getHeaders() {
        return requestHeader.getHeaders();
    }

    public Map<String, String> getQueryParams() {
        return httpStartLine.getQueryParams();
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
