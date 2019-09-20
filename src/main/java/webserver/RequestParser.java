package webserver;

import utils.IOUtils;
import utils.UrlEncodedParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private static final int MAX_BODY_SIZE = 1024 * 100; // 100KB
    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_SPLIT_LIMIT = 2;
    private static final String FIRST_LINE_DELIMITER = " ";
    private static final String PATH_QUERY_DELIMITER = "?";
    private static final String PATH_QUERY_DELIMITER_REGEX = "\\?";

    public static Request parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] firstLine = br.readLine().split(FIRST_LINE_DELIMITER);
        String method = firstLine[0];
        String url = firstLine[1].split(PATH_QUERY_DELIMITER_REGEX)[0];
        Map<String, String> queries = parseQueryString(firstLine[1]);
        Map<String, String> headers = parseHeader(br);
        byte[] body = IOUtils.readData(br, MAX_BODY_SIZE);

        return new Request(method, url, queries, headers, body);
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
}
