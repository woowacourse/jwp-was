package webserver.httpRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
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
        String[] headerLines = header.split("\n");
        for (String headerLine : headerLines) {
            String[] headerLinePair = headerLine.split(": ");
            headers.put(headerLinePair[0], headerLinePair[1]);
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }
}
