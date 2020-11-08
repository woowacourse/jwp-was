package webserver.protocol;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HttpRequest {
    private final HttpMethod method;
    private final String path;
    private final String version;
    private final Map<String, String> queryParams;
    private final RequestHeaders headers;
    private final RequestBody body;

    @Builder
    HttpRequest(final HttpMethod method, final String path, final String version,
        final Map<String, String> queryParams, final RequestHeaders headers, final RequestBody body) {
        validate(method, path, version, queryParams, headers);
        this.method = method;
        this.path = path;
        this.version = version;
        this.queryParams = queryParams;
        this.headers = headers;
        this.body = body;
    }

    private void validate(final HttpMethod method, final String path, final String version,
        final Map<String, String> queryParams, final RequestHeaders headers) {
        if (Objects.isNull(method)) {
            throw new IllegalArgumentException("httpMethod가 유효하지 않습니다: " + null);
        }
        if (Objects.isNull(path) || path.isEmpty()) {
            throw new IllegalArgumentException("path가 유효하지 않습니다: " + path);
        }
        if (Objects.isNull(version) || version.isEmpty()) {
            throw new IllegalArgumentException("httpVersion가 유효하지 않습니다: " + version);
        }
        if (Objects.isNull(queryParams)) {
            throw new IllegalArgumentException("queryParams가 유효하지 않습니다: " + null);
        }
        if (Objects.isNull(headers)) {
            throw new IllegalArgumentException("headers가 유효하지 않습니다: " + null);
        }
    }

    public boolean hasQueryParams() {
        return !queryParams.isEmpty();
    }

    public boolean hasContentLength() {
        return headers.hasContentLength();
    }

    public boolean isAcceptCSS() {
        return headers.isAcceptCSS();
    }

    public Map<String, String> getQueryParams() {
        return Collections.unmodifiableMap(queryParams);
    }
}
