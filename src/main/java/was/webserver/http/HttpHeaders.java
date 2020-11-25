package was.webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String EMPTY_STRING = "";

    private final List<HttpHeader> httpHeaders;

    public HttpHeaders(List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaders of(BufferedReader bufferedReader) throws IOException {
        List<HttpHeader> httpHeaders = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (isNotEmptyLine(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            httpHeaders.add(HttpHeader.of(line));
            line = bufferedReader.readLine();
        }

        return new HttpHeaders(httpHeaders);
    }

    private static boolean isNotEmptyLine(String line) {
        return !EMPTY_STRING.equals(line);
    }

    public static HttpHeaders emptyHeaders() {
        return new HttpHeaders(new ArrayList<>());
    }

    public boolean contains(HttpHeader httpHeader) {
        return httpHeaders.contains(httpHeader);
    }

    public String getHttpHeader(HttpHeader httpHeader) {
        return httpHeaders.stream()
                .filter(header -> header.equals(httpHeader))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getContent();
    }

    public void add(String type, String content) {
        HttpHeader httpHeader = HttpHeader.of(type, content);
        httpHeaders.add(httpHeader);
    }

    public String getHttpHeadersString() {
        return httpHeaders.stream()
                .map(HttpHeader::getHttpHeaderString)
                .collect(Collectors.joining(System.lineSeparator()))
                + System.lineSeparator();
    }

    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }
}
