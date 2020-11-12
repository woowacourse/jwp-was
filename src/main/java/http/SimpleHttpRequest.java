package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleHttpRequest implements HttpRequest {

    private HttpMethod method;
    private String path;
    private String version;
    private HttpHeaders headers;

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (Objects.nonNull(line = bufferedReader.readLine()) && !line.isEmpty()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        String httpRequestString = stringBuilder.toString();
        return of(httpRequestString);
    }

    public static HttpRequest of(String input) {
        String[] lines = input.split(System.lineSeparator());
        String startLine = lines[0];
        String[] tokens = startLine.split(" ");
        Map<String, String> headers = Arrays.stream(Arrays.copyOfRange(lines, 1, lines.length))
            .map(line -> line.split(": "))
            .collect(Collectors.toMap(
                pair -> pair[0], pair -> pair[1]
            ));
        HttpHeaders httpHeaders = new HttpHeaders(headers);
        return new SimpleHttpRequest(HttpMethod.valueOf(tokens[0]), tokens[1], tokens[2], httpHeaders);
    }

    private SimpleHttpRequest(HttpMethod httpMethod, String path, String version, HttpHeaders httpHeaders) {
        this.method = httpMethod;
        this.path = path;
        this.version = version;
        this.headers = httpHeaders;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public String getURI() {
        return path;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
