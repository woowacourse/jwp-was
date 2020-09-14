package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import utils.HttpElementExtractor;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeaders httpRequestHeaders;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String requestLine = HttpElementExtractor.extractRequestLine(bufferedReader);
        httpRequestLine = new HttpRequestLine(requestLine);

        Map<String, String> headers = HttpElementExtractor.extractHeaders(bufferedReader);
        httpRequestHeaders = new HttpRequestHeaders(headers);

        if (httpRequestLine.isPost()) {
            Map<String, String> body = HttpElementExtractor.extractBody(bufferedReader,
                httpRequestHeaders.getContentLength());
            httpRequestBody = new HttpRequestBody(body);
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
        return httpRequestHeaders.getValue(key);
    }

    public String getHttpBodyValueOf(String key) {
        return httpRequestBody.getValue(key);
    }
}
