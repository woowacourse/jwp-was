package http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpHeaders httpHeaders;
    private HttpBody httpBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        if (requestLine == null) {
            return;
        }

        httpRequestLine = new HttpRequestLine(requestLine);
        httpHeaders = new HttpHeaders(bufferedReader);

        if (httpRequestLine.isPost()) {
            httpBody = new HttpBody(bufferedReader, httpHeaders.getContentLength());
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
