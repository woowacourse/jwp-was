package webserver.http.request;

import webserver.http.common.HttpHeader;
import webserver.http.request.exception.IntervalServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {
    public static void parse(final InputStream inputStream, final HttpRequest httpRequest) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            RequestLine requestLine = RequestLine.of(bufferedReader.readLine());
            HttpHeader httpHeader = new HttpHeader(convertHeaderLines(bufferedReader));
            QueryStringParams queryStringParams = QueryStringParamsParser.parse(bufferedReader, requestLine, httpHeader);

            httpRequest.init(requestLine, httpHeader, queryStringParams);
        } catch (IOException e) {
            throw new IntervalServerException();
        }
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