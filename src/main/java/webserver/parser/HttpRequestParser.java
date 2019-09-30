package webserver.parser;

import utils.HttpRequestUtils;
import utils.IOUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.request.RequestCookie;
import webserver.request.RequestHeader;
import webserver.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestParser {
    private static final int REQUEST_LINES_REQUESTLINE_INDEX = 0;
    private static final int REQUEST_LINES_HEADER_INDEX = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException, URISyntaxException {
        List<String> requestLines = parseRequestBuffer(bufferedReader);
        RequestLine requestLine = parseRequestLine(requestLines);
        RequestHeader requestHeader = parseRequestHeader(requestLines);
        RequestCookie requestCookie = parseRequestCookie(requestHeader.getRowCookie());
        RequestBody requestBody = parseRequestBody(bufferedReader, requestHeader.getHeader("Content-Length"));
        return new HttpRequest(requestLine, requestHeader, requestCookie, requestBody);
    }

    private static List<String> parseRequestBuffer(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!"".equals(line) && null != line) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }

    private static RequestLine parseRequestLine(List<String> lines) throws URISyntaxException {
        if (lines.size() > REQUEST_LINES_REQUESTLINE_INDEX) {
            String method = lines.get(REQUEST_LINES_REQUESTLINE_INDEX).split(" ")[KEY_INDEX];
            String uri = lines.get(REQUEST_LINES_REQUESTLINE_INDEX).split(" ")[VALUE_INDEX];
            return new RequestLine(method, uri);
        }
        return null;
    }

    private static RequestHeader parseRequestHeader(List<String> lines) {
        Map<String, String> headers = new HashMap<>();
        for (int i = REQUEST_LINES_HEADER_INDEX; i < lines.size(); i++) {
            headers.put(lines.get(i).split(": ")[KEY_INDEX], lines.get(i).split(": ")[VALUE_INDEX]);
        }
        return new RequestHeader(headers);
    }

    private static RequestCookie parseRequestCookie(String rowCookie) {
        Map<String, String> cookies = new HashMap<>();
        if (rowCookie != null) {
            cookies = HttpRequestUtils.parseParamToMap(rowCookie, "; ");
        }
        return new RequestCookie(cookies);
    }

    private static RequestBody parseRequestBody(BufferedReader bufferedReader, String bodyLength) throws IOException {
        RequestBody requestBody = null;
        if (bodyLength != null) {
            requestBody = new RequestBody(IOUtils.readData(bufferedReader, Integer.parseInt(bodyLength)));
        }
        return requestBody;
    }
}
