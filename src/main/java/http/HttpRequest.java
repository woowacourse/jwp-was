package http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-Length";

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

        if (httpRequestLine.isPost()) {
            httpBody = new HttpBody(bufferedReader, Integer.parseInt(httpHeaders.getValue(CONTENT_LENGTH)));
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
