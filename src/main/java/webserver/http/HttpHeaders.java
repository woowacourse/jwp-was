package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpHeaders {
    private static final String EMPTY_STRING = "";

    private final HttpHeadersState httpHeadersState;
    private final List<HttpHeader> httpHeaders;

    public HttpHeaders(HttpHeadersState httpHeadersState, List<HttpHeader> httpHeaders) {
        this.httpHeadersState = httpHeadersState;
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

        if (isEmptyHeaders(httpHeaders)) {
            return new HttpHeaders(HttpHeadersState.EMPTY, httpHeaders);
        }
        return new HttpHeaders(HttpHeadersState.NOT_EMPTY, httpHeaders);
    }

    private static boolean isNotEmptyLine(String line) {
        return !EMPTY_STRING.equals(line);
    }

    private static boolean isEmptyHeaders(List<HttpHeader> httpHeaders) {
        return httpHeaders.isEmpty();
    }

    public HttpHeadersState getHttpHeadersState() {
        return httpHeadersState;
    }

    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }
}
