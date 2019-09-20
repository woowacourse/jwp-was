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
    private static final String BLANK = " ";
    private static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequest parse(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String[] startLine = bufferedReader.readLine().split(BLANK);
        HttpMethod httpMethod = HttpMethod.valueOf(startLine[0]);

        if (httpMethod == HttpMethod.GET) {
            return parseGet(bufferedReader, httpMethod, startLine[1], startLine[2]);
        } else if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
            return parsePost(bufferedReader, httpMethod, startLine[1], startLine[2]);
        }

        throw new IllegalArgumentException();
    }

    private static HttpRequest parseGet(final BufferedReader bufferedReader, final HttpMethod method,
                                        final String requestPath, final String version) throws IOException {
        String[] tokens = requestPath.split("\\?");
        Url url = new Url(tokens[0]);
        HttpVersion httpVersion = HttpVersion.of(version);
        HttpHeader httpHeader = HttpHeader.of(convertHeaderLines(bufferedReader));
        HttpRequestParams httpRequestParams = extractQueryParams(tokens);

        return new HttpRequest(url, method, httpVersion, httpRequestParams, httpHeader);
    }


    private static HttpRequestParams extractQueryParams(final String[] tokens) {
        if (tokens.length == 2) {
            return HttpRequestParams.of(tokens[1]);
        }
        return HttpRequestParams.init();
    }

    private static HttpRequest parsePost(final BufferedReader bufferedReader, final HttpMethod method,
                                         final String requestPath, final String version) throws IOException {
        Url url = new Url(requestPath);
        HttpVersion httpVersion = HttpVersion.of(version);
        HttpHeader httpHeader = HttpHeader.of(convertHeaderLines(bufferedReader));
        HttpRequestParams httpRequestParams = HttpRequestParams.of(
                IOUtils.readData(bufferedReader, Integer.parseInt(httpHeader.get(CONTENT_LENGTH)))
        );

        return new HttpRequest(url, method, httpVersion, httpRequestParams, httpHeader);
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