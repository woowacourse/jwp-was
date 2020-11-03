package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import webserver.http.HttpHeaders;
import webserver.utils.HttpElementExtractor;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpHeaders httpRequestHeaders;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String requestLine = HttpElementExtractor.extractRequestLine(bufferedReader);
        httpRequestLine = new HttpRequestLine(requestLine);

        Map<String, String> headers = HttpElementExtractor.extractHeaders(bufferedReader);
        httpRequestHeaders = new HttpHeaders(headers);

        if (httpRequestLine.isPost()) {
            Map<String, String> body = HttpElementExtractor.extractBody(bufferedReader,
                httpRequestHeaders.getContentLength());
            httpRequestBody = new HttpRequestBody(body);
        }
    }

    public HttpMethod getHttpMethod() {
        return httpRequestLine.getHttpMethod();
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

    public boolean hasHttpHeaderParameterOf(String key) {
        return httpRequestHeaders.hasValue(key);
    }

    public String getHttpHeaderParameterOf(String key) {
        return httpRequestHeaders.getValue(key);
    }

    public String getHttpBodyValueOf(String key) {
        return httpRequestBody.getValue(key);
    }

    public HttpHeaders getHttpRequestHeaders() {
        return httpRequestHeaders;
    }
}
