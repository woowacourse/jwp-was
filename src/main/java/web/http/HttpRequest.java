package web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    private final HttpRequestLine httpRequestLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;

    private HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequestLine httpRequestLine = HttpRequestLine.from(br.readLine());
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.from(br);
        HttpRequestBody httpRequestBody = createRequestBody(br, httpRequestLine, httpRequestHeader);

        return new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }

    private static HttpRequestBody createRequestBody(BufferedReader br, HttpRequestLine httpRequestLine,
        HttpRequestHeader httpRequestHeader) throws IOException {
        if (httpRequestLine.isPost()) {
            return HttpRequestBody.of(br, (String) httpRequestHeader.getValue("Content-Length"), httpRequestLine);
        }
        return null;
    }

    public HttpRequestBody getRequestBody() {
        return httpRequestBody;
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public Object getHeaders(String key) {
        return httpRequestHeader.getParams().get(key);
    }
}
