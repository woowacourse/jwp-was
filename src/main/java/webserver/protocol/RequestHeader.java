package webserver.protocol;

import java.util.Objects;

public class RequestHeader {

    private final String httpMethod;
    private final String path;
    private final String httpVersion;

    public RequestHeader(final String httpMethod, final String path, final String httpVersion) {
        validate(httpMethod, path, httpVersion);
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
    }

    private void validate(final String httpMethod, final String path, final String httpVersion) {
        if (Objects.isNull(httpMethod) || httpMethod.isEmpty()) {
            throw new IllegalArgumentException("httpMethod가 유효하지 않습니다: " + httpMethod);
        }
        if (Objects.isNull(path) || path.isEmpty()) {
            throw new IllegalArgumentException("path가 유효하지 않습니다: " + path);
        }
        if (Objects.isNull(httpVersion) || httpVersion.isEmpty()) {
            throw new IllegalArgumentException("httpVersion가 유효하지 않습니다: " + httpVersion);
        }
    }
}
