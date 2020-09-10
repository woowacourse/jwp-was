package http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private HttpStartLine httpStartLine;
    private HttpHeaders httpHeaders;
    private HttpBody httpBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String startLine = bufferedReader.readLine();
        if (startLine == null) {
            return;
        }

        httpStartLine = new HttpStartLine(startLine);
        httpHeaders = new HttpHeaders(bufferedReader);

        if (httpStartLine.getHttpMethod().equals(HttpMethod.POST)) {
            String body = bufferedReader.readLine();
            httpBody = new HttpBody(body);
        }
    }

    public HttpStartLine getHttpStartLine() {
        return httpStartLine;
    }

    public String getHttpPath() {
        return httpStartLine.getPath();
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
