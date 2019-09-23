package webserver.httpRequest;

import java.util.HashMap;
import java.util.Map;

import static webserver.HttpRequest.CONTENT_LENGTH;
import static webserver.HttpRequest.CONTENT_TYPE;

public class HttpRequestHeader {
    private static final String HEADER_LINE_SEPARATOR = "\n";
    private static final String HEADER_SEPARATOR = ": ";

    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public static HttpRequestHeader of(String header) {
        HashMap<String, String> headers = new HashMap<>();
        parseBody(header, headers);

        return new HttpRequestHeader(headers);
    }

    private static void parseBody(String header, HashMap<String, String> headers) {
        String[] headerLines = header.split(HEADER_LINE_SEPARATOR);
        for (String headerLine : headerLines) {
            String[] headerLinePair = headerLine.split(HEADER_SEPARATOR);
            headers.put(headerLinePair[0], headerLinePair[1]);
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(CONTENT_LENGTH));
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }
}
