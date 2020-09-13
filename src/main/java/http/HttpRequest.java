package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import utils.HttpElementExtractor;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpHeaders httpHeaders;
    private HttpBody httpBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String requestLine = HttpElementExtractor.extractRequestLine(bufferedReader);
        httpRequestLine = new HttpRequestLine(requestLine);

        Map<String, String> headers = HttpElementExtractor.extractHeaders(bufferedReader);
        httpHeaders = new HttpHeaders(headers);

        if (httpRequestLine.isPost()) {
            Map<String, String> body = HttpElementExtractor.extractBody(bufferedReader, httpHeaders.getContentLength());
            httpBody = new HttpBody(body);
        }
    }

    public String getHttpPath() {
        return httpRequestLine.getPath();
    }

    public boolean isStaticFile() {
        return httpRequestLine.isStaticFile();
    }

    public String getContentType() {
        return httpRequestLine.getContentType();
    }

    public String getHttpHeaderParameterOf(String key) {
        return httpHeaders.getValue(key);
    }

    public String getHttpBodyValueOf(String key) {
        return httpBody.getValue(key);
    }
}
