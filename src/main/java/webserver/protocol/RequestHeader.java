package webserver.protocol;

import java.util.Map;
import java.util.Objects;

public class RequestHeader {

    private final String httpMethod;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> queryParams;

    RequestHeader(final String httpMethod, final String path, final String httpVersion,
        final Map<String, String> queryParams) {
        validate(httpMethod, path, httpVersion, queryParams);
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryParams = queryParams;
    }

    private void validate(final String httpMethod, final String path, final String httpVersion,
        final Map<String, String> queryParams) {
        if (Objects.isNull(httpMethod) || httpMethod.isEmpty()) {
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
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}
