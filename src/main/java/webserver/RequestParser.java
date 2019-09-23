package webserver;

import utils.IOUtils;

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

        byte[] buf = readBody(br, headers);

        return new HttpRequest(requestLine.getMethod(), requestLine.getRequestUri(),
            requestLine.getPath(), requestLine.getQueries(), headers, buf);
    }

    private static byte[] readBody(BufferedReader br, Map<String, String> headers) throws IOException {
        byte[] buf = new byte[]{};
        String contentLengthHeader = headers.get("Content-Length");
        if (contentLengthHeader != null) {
            buf = IOUtils.readData(br, Integer.parseInt(contentLengthHeader)).getBytes();
        }
        return buf;
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
