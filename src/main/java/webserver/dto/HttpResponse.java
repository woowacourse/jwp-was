package webserver.dto;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import webserver.HttpStatusCode;

public class HttpResponse {

    private String protocol;
    private HttpStatusCode httpStatusCode;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse(String protocol, HttpStatusCode httpStatusCode,
        Map<String, String> headers, byte[] body) {
        this.protocol = Objects.requireNonNull(protocol);
        this.httpStatusCode = Objects.requireNonNull(httpStatusCode);
        this.headers
            = settingHeaders(Objects.requireNonNull(headers), Objects.requireNonNull(body));
        this.body = body;
    }

    public HttpResponse(String protocol, HttpStatusCode httpStatusCode,
        Map<String, String> headers, String body) {
        this(protocol, httpStatusCode, headers, body.getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, String> settingHeaders(Map<String, String> headers, byte[] body) {
        headers.put(CONTENT_LENGTH, String.valueOf(body.length));
        String contentType = headers.get(CONTENT_TYPE);
        if (Objects.isNull(contentType) || contentType.isEmpty()) {
            headers.put(CONTENT_TYPE, "text/plain;charset=utf-8");
            return Collections.unmodifiableMap(headers);
        }
        headers.put(CONTENT_TYPE, contentType + ";charset=utf-8");
        return Collections.unmodifiableMap(headers);
    }

    public String getProtocol() {
        return protocol;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
            "protocol='" + protocol + '\'' +
            ", httpStatusCode=" + httpStatusCode +
            ", headers=" + headers +
            ", body=" + Arrays.toString(body) +
            '}';
    }
}
