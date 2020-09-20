package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import utils.IOUtils;

public class ServletRequest {
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public ServletRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        StringBuilder headerLines = new StringBuilder();
        while (Objects.nonNull(line) && !line.isEmpty()) {
            headerLines.append(line).append("\n");
            line = br.readLine();
        }
        requestHeader = RequestHeader.of(headerLines.toString());
        requestBody = RequestBody.of(IOUtils.readData(br, requestHeader.getContentLength()));
    }

    public ServletRequest(String lines, String body) {
        requestHeader = RequestHeader.of(lines);
        requestBody = RequestBody.of(body);
    }

    public Map<String, String> getBody() {
        return requestBody.getAttribute();
    }

    public String getPath() {
        return requestHeader.getPath();
    }

    public RequestHeader.MethodType getMethod() {
        return requestHeader.getMethod();
    }

    public String getProtocolVersion() {
        return requestHeader.getProtocolVersion();
    }

    public Map<String, String> getHeaders() {
        return requestHeader.getAttribute();
    }

    public Map<String, String> getQueryParams() {
        return requestHeader.getQueryParams();
    }

    public String getHeader(String header) {
        return requestHeader.getHeader(header);
    }

    public String getAccept() {
        return requestHeader.getHeader("ACCEPT");
    }
}
