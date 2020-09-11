package http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpHeaders httpHeaders;
    private HttpBody httpBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String startLine = bufferedReader.readLine();
        if (startLine == null) {
            return;
        }

        httpRequestLine = new HttpRequestLine(startLine);
        httpHeaders = new HttpHeaders(bufferedReader);

        if (httpRequestLine.getHttpMethod().equals(HttpMethod.POST)) {
            String body = bufferedReader.readLine();
            httpBody = new HttpBody(body);
        }
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public String getHttpPath() {
        return httpRequestLine.getPath();
    }

    public String getHttpHeaderParameterOf(String key) {
        return httpHeaders.getValue(key);
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    public String getHttpBodyValueOf(String key) {
        return httpBody.getValue(key);
    }
}
