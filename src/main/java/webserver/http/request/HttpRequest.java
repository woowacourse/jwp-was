package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class HttpRequest {
    private final HttpRequestHeader httpRequestHeader;
    private final Map<String, String> body;

    public HttpRequest(HttpRequestHeader httpRequestHeader, Map<String, String> body) {
        this.httpRequestHeader = httpRequestHeader;
        this.body = body;
    }

    public HttpRequest(HttpRequestHeader httpRequestHeader) {
        this(httpRequestHeader, new HashMap<>());
    }

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        List<String> headers = new ArrayList<>();

        boolean hasBody = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isEmpty()) {
                hasBody = true;
                break;
            }
            headers.add(lines.get(i));
        }
        this.httpRequestHeader = new HttpRequestHeader(headers);

        if (hasBody) {
            this.body = ParameterParser.parse(lines.get(lines.size() - 1));
        } else {
            this.body = new HashMap<>();
        }
    }

    public HttpRequest(InputStream inputStream) throws IOException {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    public HttpMethod getHttpMethod() {
        return httpRequestHeader.getHttpRequestLine().getHttpMethod();
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(httpRequestHeader.getHttpRequestLine().getParameters());
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(httpRequestHeader.getHeaders());
    }

    public String getResourcePath() {
        return httpRequestHeader.getHttpRequestLine().getResourcePath();
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(body);
    }
}
