package http.request;

import http.common.HttpHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {
    public static void parse(final InputStream inputStream, final HttpRequest httpRequest) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = RequestLine.of(bufferedReader.readLine());
        HttpHeader httpHeader = HttpHeader.of(convertHeaderLines(bufferedReader));
        QueryStringParams queryStringParams = QueryStringParamsParser.parse(bufferedReader, requestLine, httpHeader);

        httpRequest.init(requestLine, httpHeader, queryStringParams);
    }

    private static List<String> convertHeaderLines(final BufferedReader bufferedReader) throws IOException {
        List<String> headerLines = new ArrayList<>();
        String line;

        while (!(line = bufferedReader.readLine()).equals("")) {
            headerLines.add(line);
        }

        return headerLines;
    }
}