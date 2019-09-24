package webserver;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParser {

    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_SPLIT_LIMIT = 2;

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestLine requestLine = RequestLine.from(br.readLine());
        Map<String, String> headers = parseHeader(br);
        Map<String, String> cookies = parseCookie(headers);

        Map<String, ?> body = parseBody(headers, readBody(br, headers));

        return new HttpRequest(requestLine.getMethod(), requestLine.getRequestUri(),
            requestLine.getPath(), requestLine.getQueries(), headers, cookies, body);
    }

    private static Map<String, ?> parseBody(Map<String, String> headers, byte[] buf) {
        if (buf.length == 0) {
            return Collections.emptyMap();
        }
        String contentType = headers.get("Content-Type").split(";")[0];
        return RequestContentType.from(contentType)
            .orElseThrow(() -> new UnsupportedContentType(contentType))
            .parse(buf);
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

    private static Map<String, String> parseCookie(Map<String, String> headers) {
        String cookieHeader = headers.get("Cookie");

        if (cookieHeader == null) {
            cookieHeader = headers.get("cookie");
        }

        if (cookieHeader != null) {
            return Arrays.stream(cookieHeader.split("; "))
                .collect(Collectors.toMap(
                    token -> token.split("=", 2)[0],
                    token -> token.split("=", 2)[1]));
        }
        return new HashMap<>();
    }

    private static byte[] readBody(BufferedReader br, Map<String, String> headers) throws IOException {
        byte[] buf = new byte[]{};
        String contentLengthHeader = headers.get("Content-Length");
        if (contentLengthHeader != null) {
            buf = IOUtils.readData(br, Integer.parseInt(contentLengthHeader)).getBytes();
        }
        return buf;
    }

    private static boolean hasMoreLine(String line) {
        return !(line == null || line.isEmpty());
    }
}
