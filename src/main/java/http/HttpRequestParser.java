package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParser {
    private static final String QUERY_STRING_MULTIPLE_PARAMETER_SEPARATOR = "&";
    private static final String QUERY_STRING_SEPARATOR_REGEX = "\\?";
    private static final String QUERY_STRING_SEPARATOR = "?";
    private static final String FORM_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";
    private static final String QUERY_STRING_EQUAL_SIGN = "=";
    private static final String WHITE_SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String UTF_8 = "UTF-8";

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        HttpStartLine startLine = parseStartLine(br);

        HttpHeader headers = parseHeaders(br);

        HttpBody body = null;

        if (headers.containContentLength()) {
            body = parseBody(br, startLine, headers);
        }

        return new HttpRequest.HttpRequestBuilder()
                .startLine(startLine)
                .headers(headers)
                .body(body)
                .build();
    }

    private static HttpStartLine parseStartLine(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        String[] startLineTokens = startLine.split(WHITE_SPACE);
        HttpMethod method = HttpMethod.of(startLineTokens[0]);
        if (startLineTokens[1].contains(QUERY_STRING_SEPARATOR)) {
            String[] path = startLineTokens[1].split(QUERY_STRING_SEPARATOR_REGEX);
            return new HttpStartLine(path[0], method, new RequestParameter(parseQueryString(path[1])));
        }

        return new HttpStartLine(startLineTokens[1], method);
    }

    private static Map<String, String> parseQueryString(String queryString) {
        return Arrays.stream(queryString.split(QUERY_STRING_MULTIPLE_PARAMETER_SEPARATOR))
                .map(query -> query.split(QUERY_STRING_EQUAL_SIGN))
                .collect(Collectors.toMap(q -> q[0], q -> q[1]))
                ;
    }

    private static HttpHeader parseHeaders(BufferedReader br) throws IOException {
        return new HttpHeader(readLinesBeforeEmptyLine(br));
    }

    private static HttpBody parseBody(BufferedReader br, HttpStartLine startLine, HttpHeader headers) throws IOException {
        String body = URLDecoder.decode(IOUtils.readData(br, headers.getContentLength()), UTF_8);

        if (startLine.matchMethod(HttpMethod.POST) && headers.matchContentType(FORM_CONTENT_TYPE_VALUE)) {
            RequestParameter requestBody = new RequestParameter(parseQueryString(body));
            return new HttpBody(requestBody, body);
        }

        return new HttpBody(body);
    }

    private static List<String> readLinesBeforeEmptyLine(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = br.readLine();
        while (!"".equals(line)) {
            if (line == null) {
                break;
            }
            lines.add(line);
            System.out.println(line);
            line = br.readLine();
        }

        return lines;
    }
}