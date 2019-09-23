package webserver;

import utils.IOUtils;
import utils.UrlEncodedParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParser {

    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_SPLIT_LIMIT = 2;
    private static final String FIRST_LINE_DELIMITER = " ";
    private static final String PATH_QUERY_DELIMITER = "?";
    private static final String PATH_QUERY_DELIMITER_REGEX = "\\?";
    private static final String COOKIE_HEADER_KEY = "Cookie";
    private static final String COOKIE_PAIR_DELIMITER = "; ";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";

    public static Request parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] firstLine = br.readLine().split(FIRST_LINE_DELIMITER);
        String method = firstLine[0];
        String url = firstLine[1].split(PATH_QUERY_DELIMITER_REGEX)[0];
        Map<String, String> queries = parseQueryString(firstLine[1]);
        Map<String, String> headers = parseHeader(br);
        Map<String, String> cookies = parseCookie(headers);
        byte[] body = getBody(br, headers);

        return new Request(method, url, queries, headers, cookies, body);
    }

    private static Map<String, String> parseQueryString(String pair) {
        Map<String, String> queries = new HashMap<>();
        if (pair.contains(PATH_QUERY_DELIMITER)) {
            String[] queryPairs = pair.split(PATH_QUERY_DELIMITER_REGEX);
            queries.putAll(UrlEncodedParser.parse(queryPairs[1]));
        }

        return queries;
    }

    private static Map<String, String> parseHeader(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line = br.readLine();
        while (hasMoreLine(line)) {
            String[] headerTokens = line.split(HEADER_DELIMITER, HEADER_SPLIT_LIMIT);
            headers.put(headerTokens[0], headerTokens[1]);
            line = br.readLine();
        }

        return headers;
    }

    private static boolean hasMoreLine(String line) {
        return !(line == null || line.isEmpty());
    }

    private static Map<String, String> parseCookie(Map<String, String> headers) {
        Map<String, String> cookies = new HashMap<>();

        if (headers.containsKey(COOKIE_HEADER_KEY)) {
            String[] tokens = headers.get(COOKIE_HEADER_KEY).split(COOKIE_PAIR_DELIMITER);
            Arrays.stream(tokens)
                    .map(UrlEncodedParser::parsePair)
                    .filter(Objects::nonNull)
                    .forEach(tuple -> cookies.put(tuple.getKey(), tuple.getValue()));
        }
        return cookies;
    }

    private static byte[] getBody(BufferedReader br, Map<String, String> headers) throws IOException {
        byte[] body = new byte[]{};
        String contentLength = headers.get(CONTENT_LENGTH_HEADER_KEY);
        if (contentLength != null) {
            body = IOUtils.readData(br, Integer.parseInt(contentLength));
        }

        return body;
    }
}
