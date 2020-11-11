package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import utils.IOUtils;

public class HttpRequest {
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;
    private final String body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.httpRequestLine = HttpRequestLine.from(br.readLine());
        this.httpHeaders = HttpHeaders.from(br);
        String body = "";
        if (httpHeaders.get("Content-Length") != null) {
            body = IOUtils.readData(br, Integer.parseInt(httpHeaders.get("Content-Length")));
        }
        this.body = body;
    }

    public boolean isBodyExist() {
        return !body.equals("");
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUrl() {
        return this.httpRequestLine.getUrl();
    }

    public String getVersion() {
        return this.httpRequestLine.getVersion();
    }

    public String getParam(String param) {
        if (isBodyExist()) {
            return Parameters.parse(body).get(param);
        }
        return httpRequestLine.getQueryParam(param);
    }

    public String getHeader(String headerType) {
        return httpHeaders.get(headerType);
    }
}
