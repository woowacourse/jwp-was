package jwp.was.dto;

import com.google.common.net.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import jwp.was.util.HttpStatusCode;

public class HttpResponse {

    private static final String CHARSET_UTF_8 = ";charset=utf-8";
    private static final String TEXT_PLAIN = "text/plain";

    private final String httpVersion;
    private final HttpStatusCode httpStatusCode;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpResponse(String httpVersion, HttpStatusCode httpStatusCode,
        Map<String, String> headers, byte[] body) {
        this.httpVersion = Objects.requireNonNull(httpVersion);
        this.httpStatusCode = Objects.requireNonNull(httpStatusCode);
        this.headers = settingHeaders(Objects.requireNonNull(headers),
            Objects.requireNonNull(body));
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
        headers.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
        String contentType = headers.get(HttpHeaders.CONTENT_TYPE);
        if (Objects.isNull(contentType) || contentType.isEmpty()) {
            headers.put(HttpHeaders.CONTENT_TYPE, TEXT_PLAIN + CHARSET_UTF_8);
            return Collections.unmodifiableMap(headers);
        }
        headers.put(HttpHeaders.CONTENT_TYPE, contentType + CHARSET_UTF_8);
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
