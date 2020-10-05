package jwp.was.webserver.dto;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import jwp.was.webserver.HttpStatusCode;

public class HttpResponse {

    private final String httpVersion;
    private final HttpStatusCode httpStatusCode;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpResponse(String httpVersion, HttpStatusCode httpStatusCode,
        Map<String, String> headers, byte[] body) {
        this.httpVersion = Objects.requireNonNull(httpVersion);
        this.httpStatusCode = Objects.requireNonNull(httpStatusCode);
        this.headers
            = settingHeaders(Objects.requireNonNull(headers), Objects.requireNonNull(body));
        this.body = body;
    }

    public static HttpResponse of(String httpVersion, HttpStatusCode httpStatusCode,
        Map<String, String> headers, byte[] body) {
        return new HttpResponse(httpVersion, httpStatusCode, headers, body);
    }

    public static HttpResponse of(String httpVersion, HttpStatusCode httpStatusCode,
        Map<String, String> headers, String body) {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        return new HttpResponse(httpVersion, httpStatusCode, headers, bodyBytes);
    }

    public static HttpResponse of(String httpVersion, HttpStatusCode httpStatusCode, String body) {
        return HttpResponse.of(httpVersion, httpStatusCode, new HashMap<>(), body);
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

    public String getHttpVersion() {
        return httpVersion;
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
            "httpVersion='" + httpVersion + '\'' +
            ", httpStatusCode=" + httpStatusCode +
            ", headers=" + headers +
            ", body=" + Arrays.toString(body) +
            '}';
    }
}
