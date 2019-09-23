package http.request;

import http.HttpHeaders;
import http.exception.EmptyHttpRequestException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.net.URLDecoder.decode;

public class HttpRequestFactory {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int STARTING_INDEX_OF_HEADER_FIELD = 1;
    private static final String EMPTY = "";

    private HttpRequestFactory() {
    }

    public static HttpRequest makeHttpRequest(InputStream in) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8.name()));
        List<String> lines = getHeaderLines(buffer);

        String requestLine = lines.get(REQUEST_LINE_INDEX);
        List<String> headerLines = extractHeaderLinesFrom(lines);

        HttpRequestLine httpRequestLine = HttpRequestLine.of(requestLine);
        HttpHeaders headers = HttpHeaders.of(headerLines);
        String body = getBody(buffer, headers);
        return new HttpRequest(httpRequestLine, headers, body);
    }

    private static List<String> getHeaderLines(BufferedReader buffer) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while (!EMPTY.equals(line = buffer.readLine()) && line != null) {
            lines.add(line);
        }
        checkEmptyHttpRequest(lines);
        return lines;
    }

    private static void checkEmptyHttpRequest(List<String> lines) {
        if (lines.isEmpty()) {
            throw new EmptyHttpRequestException();
        }
    }

    private static List<String> extractHeaderLinesFrom(List<String> lines) {
        return lines.subList(STARTING_INDEX_OF_HEADER_FIELD, lines.size());
    }

    private static String getBody(BufferedReader buffer, HttpHeaders headers) throws IOException {
        if (!headers.hasContentLength()) {
            return EMPTY;
        }

        int contentLength = Integer.parseInt(headers.getHeader("Content-Length"));
        return decode(IOUtils.readData(buffer, contentLength), StandardCharsets.UTF_8.name());
    }
}
