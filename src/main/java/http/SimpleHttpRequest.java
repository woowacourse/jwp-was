package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class SimpleHttpRequest implements HttpRequest {

    public static final int START_LINE_INDEX = 0;
    public static final int HEADER_LINE_START_INDEX = 1;
    private StartLine startLine;
    private HttpHeaders headers;

    public static SimpleHttpRequest of(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (Objects.nonNull(line = bufferedReader.readLine()) && !line.isEmpty()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        String httpRequestString = stringBuilder.toString();
        return of(httpRequestString);
    }

    public static SimpleHttpRequest of(String input) {
        String[] lines = input.split(System.lineSeparator());
        StartLine startLine = StartLine.from(lines[START_LINE_INDEX]);
        int lastHeaderIndex = extractLastHeaderIndex(lines);
        HttpHeaders httpHeaders = HttpHeaders.from(Arrays.copyOfRange(lines, HEADER_LINE_START_INDEX, lastHeaderIndex));
        return new SimpleHttpRequest(startLine, httpHeaders);
    }

    private SimpleHttpRequest(StartLine startLine, HttpHeaders httpHeaders) {
        this.startLine = startLine;
        this.headers = httpHeaders;
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
    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getVersion() {
        return startLine.getVersion();
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
