package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.httpRequestLine = HttpRequestLine.from(br.readLine());
        this.httpHeaders = HttpHeaders.from(br);
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public Map<String, String> getBody() {
        return httpRequestLine.getBody();
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }
}
