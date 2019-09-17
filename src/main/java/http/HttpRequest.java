package http;

import http.exception.EmptyHttpRequestException;
import http.exception.StartLineException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static final int START_LINE_INDEX = 0;
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int START_LINE_SIZE = 3;

    private HttpMethod method;
    private String url;
    private HttpVersion version;
    private Map<String, String> attributes;

    private HttpRequest(BufferedReader bufferedReader) throws IOException {
        List<String> lines = parseRequest(bufferedReader);
        checkEmptyHttpRequest(lines);

        String[] parsedStartLine = parseStartLine(lines.get(START_LINE_INDEX));
        checkStartLine(parsedStartLine);
        method = HttpMethod.resolve(parsedStartLine[HTTP_METHOD_INDEX]);
        url = parsedStartLine[PATH_INDEX];
        version = HttpVersion.resolve(parsedStartLine[HTTP_VERSION_INDEX]);
    }

    private void checkStartLine(String[] parsedStartLine) {
        if (parsedStartLine.length != START_LINE_SIZE) {
            throw new StartLineException();
        }
    }

    private void checkEmptyHttpRequest(List<String> lines) {
        if (lines.isEmpty()) {
            throw new EmptyHttpRequestException();
        }
    }

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        return new HttpRequest(bufferedReader);
    }

    private List<String> parseRequest(BufferedReader buffer) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while (!"".equals(line = buffer.readLine()) && line != null) {
            lines.add(line);
        }
        return lines;
    }

    private String[] parseStartLine(String startLine) {
        return startLine.split(" ");
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "\n" + method + " " + url + " " + version + "\n";
    }
}
