package webserver.http.request;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Map<String, Object> headers;
    private final Map<String, String> body;

    public HttpRequest(HttpRequestLine requestLine, Map<String, Object> headers, Map<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpRequest(HttpRequestLine requestLine, Map<String, String> body) {
        this(requestLine, new HashMap<>(), body);
    }

    public HttpRequest(HttpRequestLine requestLine) {
        this(requestLine, new HashMap<>());
    }

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        this.requestLine = new HttpRequestLine(bufferedReader.readLine());

        boolean hasBody = false;
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                hasBody = true;
            }
            lines.add(line);
        }

        this.headers = extractHeaders(hasBody, lines);
        this.body = extractBody(hasBody, lines);
    }

    private Map<String, Object> extractHeaders(boolean hasBody, List<String> lines) throws UnsupportedEncodingException {
        Map<String, Object> headers = new HashMap<>();
        int headerSize = hasBody ? lines.size() - 1 : lines.size();
        for (int i = 0; i < headerSize; i++) {
            if (Strings.isNullOrEmpty(lines.get(i))) {
                continue;
            }
            Parser.Pair pair = Parser.parseHeader(lines.get(i));
            headers.put(pair.getKey(), pair.getValue());
        }
        return headers;
    }

    private Map<String, String> extractBody(boolean hasBody, List<String> lines) throws UnsupportedEncodingException {
        if (hasBody) {
            return Parser.parseParameter(lines.get(lines.size() - 1));
        }
        return new HashMap<>();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public Object getHeader(String httpHeader) {
        return headers.get(httpHeader);
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(requestLine.getParameters());
    }

    public String getResourcePath() {
        return requestLine.getResourcePath();
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(body);
    }
}
