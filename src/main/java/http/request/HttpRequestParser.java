package http.request;

import http.common.HttpHeader;
import http.common.HttpVersion;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {
    public static final String QUERY_STRING_DELIMITER = "\\?";
    private static final String BLANK = " ";
    private static final String LAST_HEADER_LINE = "";

    public static HttpRequest parse(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = parseRequestLine(bufferedReader.readLine());
        HttpRequestParams requestParams = parseQueryParams(requestLine);
        HttpHeader httpHeader = parseRequestHeader(convertHeaderLines(bufferedReader));
        HttpRequestBody httpRequestBody = parseRequestBody(bufferedReader, httpHeader.getContentLength());

        return new HttpRequest(requestLine, requestParams, httpHeader, httpRequestBody);
    }

    private static RequestLine parseRequestLine(String requestLine) {
        String[] startLine = requestLine.split(BLANK);

        return new RequestLine(
                new Url(startLine[1]),
                HttpMethod.valueOf(startLine[0]),
                HttpVersion.of(startLine[2]));
    }

    private static HttpHeader parseRequestHeader(List<String> rawHeaderLines) {
        return HttpHeader.of(rawHeaderLines);
    }

    private static HttpRequestParams parseQueryParams(final RequestLine requestLine) {
        String[] tokens = requestLine.getUrl().getRawUrl().split(QUERY_STRING_DELIMITER);

        if (tokens.length == 2) {
            return HttpRequestParams.of(tokens[1]);
        }
        return HttpRequestParams.init();
    }

    private static List<String> convertHeaderLines(final BufferedReader bufferedReader) throws IOException {
        List<String> headerLines = new ArrayList<>();
        String line;

        while (!(line = bufferedReader.readLine()).equals(LAST_HEADER_LINE)) {
            headerLines.add(line);
        }

        return headerLines;
    }

    private static HttpRequestBody parseRequestBody(BufferedReader bufferedReader,
                                                    int contentLength) throws IOException {
        return new HttpRequestBody(IOUtils.readData(bufferedReader, contentLength));
    }
}