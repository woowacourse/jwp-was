package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
        System.out.println(body);
    }

    public boolean isBodyExist() {
        return !body.equals("");
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getParams() {
        return Parameters.parse(body).getParams();
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }
}
