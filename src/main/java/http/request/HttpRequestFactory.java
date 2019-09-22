package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.exception.EmptyHttpRequestException;
import http.exception.RequestLineException;
import utils.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.URLDecoder.decode;

public class HttpRequestFactory {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final int START_LINE_SIZE = 3;
    private static final int HEADER_STARTING_INDEX = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String START_LINE_DELIMITER = " ";
    private static final String HEADER_DELIMITER = ":\\s+";
    private static final String EMPTY = "";

    public static HttpRequest makeHttpRequest(InputStream in) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8.name()));
        List<String> lines = getHeaderLines(buffer);

        HttpRequestLine requestLine = parseHttpRequestLine(lines.get(REQUEST_LINE_INDEX));
        HttpHeaders headers = parseHttpHeaders(extractHeaders(lines));
        String body = getBody(buffer, headers);
        return new HttpRequest(requestLine, headers, body);
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

    private static HttpRequestLine parseHttpRequestLine(String requestLine) throws UnsupportedEncodingException {
        String[] parsedStartLine = requestLine.split(START_LINE_DELIMITER);
        checkStartLine(parsedStartLine);
        HttpMethod method = HttpMethod.resolve(parsedStartLine[HTTP_METHOD_INDEX]);
        HttpUri url = new HttpUri(parsedStartLine[PATH_INDEX]);
        HttpVersion version = HttpVersion.resolve(parsedStartLine[HTTP_VERSION_INDEX]);
        return new HttpRequestLine(method, url, version);
    }

    private static List<String> extractHeaders(List<String> lines) {
        return lines.subList(HEADER_STARTING_INDEX, lines.size());
    }

    private static HttpHeaders parseHttpHeaders(List<String> lines) {
        Map<String, String> headers = new HashMap<>();
        for (String header : lines) {
            String[] splicedHeader = header.split(HEADER_DELIMITER);
            headers.put(splicedHeader[KEY_INDEX], splicedHeader[VALUE_INDEX]);
        }
        return new HttpHeaders(headers);
    }

    private static void checkStartLine(String[] parsedStartLine) {
        if (parsedStartLine.length != START_LINE_SIZE) {
            throw new RequestLineException();
        }
    }

    private static String getBody(BufferedReader buffer, HttpHeaders headers) throws IOException {
        if (!headers.hasContentLength()) {
            return EMPTY;
        }

        int contentLength = Integer.parseInt(headers.getHeader("Content-Length"));
        return decode(IOUtils.readData(buffer, contentLength), StandardCharsets.UTF_8.name());
    }
}
