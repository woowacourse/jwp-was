package http;

import http.exception.EmptyHttpRequestException;
import http.exception.StartLineException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.net.URLDecoder.decode;

public class HttpRequest {
    private static final int START_LINE_INDEX = 0;
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int START_LINE_SIZE = 3;
    private static final int HEADER_STARTING_INDEX = 1;

    private static final String START_LINE_DELIMITER = " ";

    private HttpMethod method;
    private HttpUrl url;
    private HttpVersion version;
    private HttpHeaders headers;
    private String body;

    private HttpRequest(BufferedReader bufferedReader) throws IOException {
        parseRequest(bufferedReader);
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

        checkEmptyHttpRequest(lines);

        String[] parsedStartLine = parseStartLine(lines.get(START_LINE_INDEX));
        checkStartLine(parsedStartLine);
        method = HttpMethod.resolve(parsedStartLine[HTTP_METHOD_INDEX]);
        url = new HttpUrl(parsedStartLine[PATH_INDEX]);
        version = HttpVersion.resolve(parsedStartLine[HTTP_VERSION_INDEX]);

        headers = new HttpHeaders(lines.subList(HEADER_STARTING_INDEX, lines.size()));

        if (headers.hasContentLength()) {
            int contentLength = Integer.parseInt(headers.getHeader("Content-Length"));
            body = decode(IOUtils.readData(buffer, contentLength), StandardCharsets.UTF_8.name());
        }
        return lines;
    }

    private String[] parseStartLine(String startLine) {
        return startLine.split(START_LINE_DELIMITER);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpUrl getUrl() {
        return url;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public QueryParams getQueryParams() {
        if (method.match(HttpMethod.GET)) {
            return url.getQueryParams();
        }
        if (method.match(HttpMethod.POST)) {
            return new QueryParams(body);
        }
        return null;
    }

    @Override
    public String toString() {
        return "\n" + method + " " + url.getPath() + " " + version + "\n" + headers + "\n";
    }
}
