package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestFactory {
    public static HttpRequest create(BufferedReader bufferedReader) throws IOException {
        List<String> lines = IOUtils.parseHeader(bufferedReader);

        HttpStartLine httpStartLine = new HttpStartLine(lines.get(0));

        Map<String, String> headers = getHeaders(lines.subList(1, lines.size()));
        HttpCookie cookie = getCookie(headers);

        HttpRequestHeader header = new HttpRequestHeader(headers);

        if ("POST".equals(httpStartLine.getMethod())) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(header.getHeader("content-length")));
            HttpRequestBody httpRequestBody = new HttpRequestBody(body);
            return new HttpRequest(httpStartLine, header, cookie, httpRequestBody);
        }
        return new HttpRequest(httpStartLine, header, cookie);
    }

    private static HttpCookie getCookie(final Map<String, String> headers) {
        if (headers.containsKey("cookie")) {
            String cookie = headers.get("cookie");
            headers.remove("cookie");
            return new HttpCookie(cookie);
        }
        return new HttpCookie();
    }

    private static Map<String, String> getHeaders(final List<String> lines) {
        return lines.stream()
                .map(HttpRequestLine::new)
                .collect(Collectors.toMap(HttpRequestLine::getKey, HttpRequestLine::getValue));
    }
}
