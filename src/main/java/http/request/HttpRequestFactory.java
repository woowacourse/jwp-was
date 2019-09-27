package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestFactory {
    public static HttpRequest create(BufferedReader bufferedReader) throws IOException {
        List<String> lines = IOUtils.parseHeader(bufferedReader);
        HttpStartLine httpStartLine = new HttpStartLine(lines.get(0));
        Map<String, String> headers = getHeaders(lines.subList(1, lines.size()));
        Map<String, String> cookies = getCookie(headers);
        HttpRequestHeader header = new HttpRequestHeader(lines);

        if ("POST".equals(header.getMethod())) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(header.get("content-length")));
            HttpRequestBody httpRequestBody = new HttpRequestBody(body);
            return new HttpRequest(header, httpRequestBody);
        }
        return new HttpRequest(header);
    }

    private static Map<String, String> getCookie(Map<String, String> headers) {
        if (headers.containsKey("Cookie")) {
            headers.get("Cookie");
            headers.remove("Cookie");
        }
        return null;
    }

    private static Map<String, String> getHeaders(final List<String> lines) {
        return lines.stream()
                .map(HttpRequestLine::new)
                .collect(Collectors.toMap(HttpRequestLine::getKey, HttpRequestLine::getValue));
    }
}
