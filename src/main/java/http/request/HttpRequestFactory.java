package http.request;

import http.HttpHeaders;
import http.exception.EmptyHttpRequestException;
import http.session.Cookie;
import utils.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static http.HttpHeaders.CONTENT_LENGTH;
import static http.HttpHeaders.COOKIE;
import static java.net.URLDecoder.decode;

public class HttpRequestFactory {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int STARTING_INDEX_OF_HEADER_FIELD = 1;
    private static final String EMPTY = "";

    private HttpRequestFactory() {
    }

    public static HttpRequest getHttpRequest(InputStream in) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8.name()));
        List<String> lines = getHeaderLines(buffer);

        String requestLine = lines.get(REQUEST_LINE_INDEX);
        List<String> headerLines = extractHeaderLinesFrom(lines);

        HttpRequestLine httpRequestLine = HttpRequestLine.parse(requestLine);
        HttpHeaders headers = HttpHeaders.parse(headerLines);

        String cookieString = headers.getHeader(COOKIE);
        Cookie cookies = Cookie.parse(cookieString);

        String body = getBody(buffer, headers);
        QueryParams queryParams = getQueryParams(requestLine, body);
        return new HttpRequest(httpRequestLine, headers, body, queryParams, cookies);
    }

    private static List<String> getHeaderLines(BufferedReader buffer) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;

        while (!EMPTY.equals(line = buffer.readLine()) && line != null) {
            lines.add(line);
        }
        checkEmpty(lines);
        return lines;
    }

    private static void checkEmpty(List<String> lines) {
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

        int contentLength = Integer.parseInt(headers.getHeader(CONTENT_LENGTH));
        return decode(IOUtils.readData(buffer, contentLength), StandardCharsets.UTF_8.name());
    }

    private static QueryParams getQueryParams(String requestLine, String body) throws UnsupportedEncodingException {
        return QueryParams.canParse(requestLine)
                ? QueryParams.parseRequestLine(requestLine)
                : QueryParams.parseBody(body);
    }
}
