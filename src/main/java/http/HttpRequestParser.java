package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    private static final String QUERY_STRING_MULTIPLE_PARAMETER_SEPARATOR = "&";
    private static final String QUERY_STRING_SEPARATOR_REGEX = "\\?";
    private static final String QUERY_STRING_SEPARATOR = "?";
    private static final String FORM_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";
    private static final String QUERY_STRING_EQUAL_SIGN = "=";
    private static final String WHITE_SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String UTF_8 = "UTF-8";

    public static HttpRequest parse(InputStream in) {
        HttpRequest.HttpRequestBuilder builder = new HttpRequest.HttpRequestBuilder();

        try {
            List<String> message = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            message.add(line);
            HttpMethod method = parseStartLine(line, builder);
            HttpHeader headers = parseHeaders(br, builder, message);

            if (headers.containContentLength()) {
                String body = parseBody(br, method, headers, builder);
                message.add(body);
            }

            log.info(String.join(NEW_LINE, message));

            return builder.build();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    private static HttpMethod parseStartLine(String startLine, HttpRequest.HttpRequestBuilder builder) {
        String[] startLineTokens = startLine.split(WHITE_SPACE);
        HttpMethod method = HttpMethod.of(startLineTokens[0]);
        builder.method(method);
        if (startLineTokens[1].contains(QUERY_STRING_SEPARATOR)) {
            String[] path = startLineTokens[1].split(QUERY_STRING_SEPARATOR_REGEX);
            builder.uri(path[0])
                    .requestParameter(new RequestParameter(parseQueryString(path[1])));
            return method;
        }

        builder.uri(startLineTokens[1]);
        return method;
    }

    private static Map<String, String> parseQueryString(String queryString) {
        return Arrays.stream(queryString.split(QUERY_STRING_MULTIPLE_PARAMETER_SEPARATOR))
                .map(query -> query.split(QUERY_STRING_EQUAL_SIGN))
                .collect(Collectors.toMap(q -> q[0], q -> q[1]))
                ;
    }

    private static HttpHeader parseHeaders(BufferedReader br, HttpRequest.HttpRequestBuilder builder, List<String> message) throws IOException {
        List<String> headerLines = new ArrayList<>();
        String header = br.readLine();
        while (!"".equals(header)) {
            if (header == null) {
                break;
            }
            headerLines.add(header);
            header = br.readLine();
        }
        HttpHeader headers = new HttpHeader(headerLines);
        builder.headers(headers);

        message.addAll(headerLines);
        message.add("");

        return headers;
    }

    private static String parseBody(BufferedReader br, HttpMethod method, HttpHeader headers, HttpRequest.HttpRequestBuilder builder) throws IOException {
        String body = IOUtils.readData(br, headers.getContentLength());
        if (method.equals(HttpMethod.POST)
                && headers.matchContentType(FORM_CONTENT_TYPE_VALUE)) {
            RequestParameter requestBody = new RequestParameter(parseQueryString(URLDecoder.decode(body, UTF_8)));
            builder.requestBody(requestBody);
        }

        builder.body(body);
        return body;
    }
}
