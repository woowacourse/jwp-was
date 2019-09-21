package webserver;

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

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestLine requestLine = RequestLine.from(br.readLine());
        Map<String, String> headers = parseHeader(br);

        char[] buf = new char[MAX_BODY_SIZE];
        readToBuffer(br, buf);

        return new HttpRequest(requestLine.getMethod(), requestLine.getRequestUri(),
            requestLine.getPath(), requestLine.getQueries(), headers, new String(buf).getBytes());
    }

    private static void readToBuffer(BufferedReader br, char[] buf) throws IOException {
        if (br.ready()) {
            br.read(buf);
        }
    }

    private static Map<String, String> parseHeader(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        line = br.readLine();
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
