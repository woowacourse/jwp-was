package webserver.http.request;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Map<String, String> body;

    public HttpRequest(HttpRequestLine requestLine, Map<String, String> body) {
        this.requestLine = requestLine;
        this.body = body;
    }

    public HttpRequest(HttpRequestLine requestLine) {
        this(requestLine, new HashMap<>());
    }

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        this.requestLine = new HttpRequestLine(bufferedReader.readLine());

        while (bufferedReader.ready()
                && !Strings.isNullOrEmpty(bufferedReader.readLine())) {
        }

        String line;
        this.body = bufferedReader.ready() && (line = bufferedReader.readLine()) != null
                ? ParameterParser.parse(line)
                : new HashMap<>();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
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
