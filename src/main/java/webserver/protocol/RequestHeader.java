package webserver.protocol;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestHeader {

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String TEXT_CSS = "text/css,*/*;q=0.1";
    private static final String ACCEPT = "Accept";

    private final HttpMethod httpMethod;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> queryParams;
    private final Map<String, String> headers;

    @Builder
    RequestHeader(final HttpMethod httpMethod, final String path, final String httpVersion,
        final Map<String, String> queryParams, final Map<String, String> headers) {
        validate(httpMethod, path, httpVersion, queryParams, headers);
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryParams = queryParams;
        this.headers = headers;
    }

    private void validate(final HttpMethod httpMethod, final String path, final String httpVersion,
        final Map<String, String> queryParams, final Map<String, String> headers) {
        if (Objects.isNull(httpMethod)) {
            throw new IllegalArgumentException("httpMethod가 유효하지 않습니다: " + httpMethod);
        }
        if (Objects.isNull(path) || path.isEmpty()) {
            throw new IllegalArgumentException("path가 유효하지 않습니다: " + path);
        }
        if (Objects.isNull(httpVersion) || httpVersion.isEmpty()) {
            throw new IllegalArgumentException("httpVersion가 유효하지 않습니다: " + httpVersion);
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
        return headers.containsKey(CONTENT_LENGTH);
    }

    public boolean isAcceptCSS() {
        return Objects.equals(TEXT_CSS, headers.get(ACCEPT));
    }

    public Map<String, String> getQueryParams() {
        return Collections.unmodifiableMap(queryParams);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
}
