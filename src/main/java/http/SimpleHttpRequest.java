package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import utils.IOUtils;

public class SimpleHttpRequest implements HttpRequest {
    public static final int START_LINE_INDEX = 0;
    public static final int HEADER_LINE_START_INDEX = 1;

    private StartLine startLine;
    private HttpHeaders headers;
    private RequestBody requestBody;
    private String rawRequest;

    public static SimpleHttpRequest of(BufferedReader bufferedReader) throws IOException {
        String startLineString = bufferedReader.readLine();
        StartLine startLine = StartLine.from(startLineString);
        String requestHeadersString = extractRequestHeader(bufferedReader);
        HttpHeaders httpHeaders = HttpHeaders.from(requestHeadersString);
        String requestBodyString = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
        RequestBody requestBody = RequestBody.from(requestBodyString);
        return new SimpleHttpRequest(startLine, httpHeaders, requestBody, joinRequest(startLineString, requestHeadersString, requestBodyString));
    }

    private static String extractRequestHeader(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (Objects.nonNull(line = bufferedReader.readLine()) && !line.isEmpty()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private static String joinRequest(String startLine, String httpHeaders, String requestBody) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startLine);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(httpHeaders);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(requestBody);
        return stringBuilder.toString();
    }

    private SimpleHttpRequest(StartLine startLine, HttpHeaders httpHeaders, RequestBody requestBody, String rawRequest) {
        this.startLine = startLine;
        this.headers = httpHeaders;
        this.requestBody = requestBody;
        this.rawRequest = rawRequest;
    }

    @Override
    public HttpMethod getMethod() {
        return startLine.getHttpMethod();
    }

    @Override
    public String getURI() {
        return startLine.getPath();
    }

    @Override
    public String getQuery() {
        return startLine.getQuery();
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public RequestBody getBody() {
        return requestBody;
    }

    public String getVersion() {
        return startLine.getVersion();
    }

    @Override
    public String toString() {
        return rawRequest;
    }

    private static int extractLastHeaderIndex(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if ("".equals(lines[i])) {
                return i;
            }
        }
        return lines.length;
    }
}
