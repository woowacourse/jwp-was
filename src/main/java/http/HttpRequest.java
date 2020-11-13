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
        String headerType = HeaderType.CONTENT_LENGTH.getTypeName();
        if (httpHeaders.get(headerType) != null) {
            body = IOUtils.readData(br, Integer.parseInt(httpHeaders.get(headerType)));
        }
        this.body = body;
    }

    public boolean isBodyExist() {
        return !body.isEmpty();
    }

    public boolean matchMethod(HttpMethod httpMethod) {
        return httpRequestLine.isMethod(httpMethod);
    }

    public String getPath() {
        return httpRequestLine.getPath();
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

    public MimeType getMimeType() {
        return httpRequestLine.getMimeType();
    }
}
