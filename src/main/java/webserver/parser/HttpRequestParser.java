package webserver.parser;

import utils.HttpRequestUtils;
import utils.IOUtils;
import webserver.http.request.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestParser {
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final int REQUEST_LINES_REQUESTLINE_INDEX = 0;
    private static final int REQUEST_LINES_HEADER_INDEX = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int VERSION_INDEX = 1;
    private static final String COOKIE_KEY = "Cookie";

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        List<String> requestLines = parseRequestBuffer(bufferedReader);
        RequestLine requestLine = parseRequestLine(requestLines);
        RequestHeader requestHeader = parseRequestHeader(requestLines);
        RequestBody requestBody = parseRequestBody(bufferedReader, requestHeader.getHeader(CONTENT_LENGTH_KEY));
        Cookie cookie = parseCookie(requestHeader.getHeader(COOKIE_KEY));
        return new HttpRequest(requestLine, requestHeader, requestBody, cookie);
    }

    private static List<String> parseRequestBuffer(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        for (String line = bufferedReader.readLine(); HttpRequestUtils.isLineEmpty(line); line = bufferedReader.readLine()) {
            lines.add(line);
        }
        return lines;
    }

    private static RequestLine parseRequestLine(List<String> lines) {
        String method = lines.get(REQUEST_LINES_REQUESTLINE_INDEX).split(REQUEST_LINE_DELIMITER)[METHOD_INDEX];
        String uri = lines.get(REQUEST_LINES_REQUESTLINE_INDEX).split(REQUEST_LINE_DELIMITER)[URL_INDEX];
        String httpVersion = lines.get(REQUEST_LINES_REQUESTLINE_INDEX).split(REQUEST_LINE_DELIMITER)[VERSION_INDEX];
        return new RequestLine(method, uri, httpVersion);
    }

    private static RequestHeader parseRequestHeader(List<String> lines) {
        Map<String, String> headers = new HashMap<>();
        for (int i = REQUEST_LINES_HEADER_INDEX; i < lines.size(); i++) {
            headers.put(lines.get(i).split(HEADER_KEY_VALUE_DELIMITER)[KEY_INDEX], lines.get(i).split(HEADER_KEY_VALUE_DELIMITER)[VALUE_INDEX]);
        }
        return new RequestHeader(headers);
    }

    private static RequestBody parseRequestBody(BufferedReader bufferedReader, String bodyLength) throws IOException {
        RequestBody requestBody = null;
        if (bodyLength != null) {
            requestBody = new RequestBody(IOUtils.readData(bufferedReader, Integer.parseInt(bodyLength)));
        }
        return requestBody;
    }

    private static Cookie parseCookie(String line) {
        return new Cookie(line);
    }
}
