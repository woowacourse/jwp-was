package http;

import jdk.internal.joptsimple.internal.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParser {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    private static final String QUERY_STRING_MULTIPLE_PARAMETER_SEPARATOR = "&";
    private static final String QUERY_STRING_SEPARATOR_REGEX = "\\?";
    private static final String QUERY_STRING_SEPARATOR = "?";
    private static final String FORM_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";
    private static final String QUERY_STRING_EQUAL_SIGN = "=";
    private static final String WHITE_SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String UTF_8 = "UTF-8";

    public static HttpRequest parse(List<String> httpRequestLines) throws UnsupportedEncodingException {
        log.info(String.join(NEW_LINE, httpRequestLines));

        HttpStartLine startLine = parseStartLine(httpRequestLines.get(0));

        HttpHeader headers = parseHeaders(httpRequestLines);

        HttpBody body = null;

        if (headers.containContentLength()) {
            body = parseBody(httpRequestLines.subList(httpRequestLines.indexOf(Strings.EMPTY) + 1, httpRequestLines.size()), startLine, headers);
        }

        return new HttpRequest.HttpRequestBuilder()
                .startLine(startLine)
                .headers(headers)
                .body(body)
                .build();
    }

    private static HttpStartLine parseStartLine(String startLine) {
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

    private static HttpHeader parseHeaders(List<String> httpRequestLines) {
        int lastIndex;

        if ((lastIndex = httpRequestLines.indexOf(Strings.EMPTY)) == -1) {
            lastIndex = httpRequestLines.size();
        }

        return new HttpHeader(httpRequestLines.subList(1, lastIndex));
    }

    private static HttpBody parseBody(List<String> bodyLines, HttpStartLine startLine, HttpHeader headers) throws UnsupportedEncodingException {
        if (startLine.matchMethod(HttpMethod.POST) && headers.matchContentType(FORM_CONTENT_TYPE_VALUE)) {
            String body = bodyLines.get(bodyLines.size() - 1);
            String queryString = URLDecoder.decode(body, UTF_8);
            RequestParameter requestBody = new RequestParameter(parseQueryString(queryString));
            return new HttpBody(requestBody, body);
        }

        return new HttpBody(bodyLines);
    }
}