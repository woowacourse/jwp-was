package webserver.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {

    private static final int QUERY_PARAM_INDEX = 1;
    private static final String QUERY_PARAMS_REGEX = "&";
    private static final String PARAM_REGEX = "=";
    private static final int QUERY_PARAM_NAME_INDEX = 0;
    private static final int QUERY_PARAM_VALUE_INDEX = 1;

    private final String httpMethod;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> queryParams;

    public RequestHeader(final String httpMethod, final String path, final String httpVersion) {
        validate(httpMethod, path, httpVersion);
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryParams = parseQueryParams(path);
    }

    private Map<String, String> parseQueryParams(final String path) {
        final String[] pathTokens = path.split("\\?", -1);
        if (pathTokens.length <= QUERY_PARAM_INDEX) {
            return new HashMap<>();
        }

        final String[] allQueryParams = pathTokens[QUERY_PARAM_INDEX].split(QUERY_PARAMS_REGEX, -1);
        final Map<String, String> queryParams = new HashMap<>();
        for (final String queryParam : allQueryParams) {
            final String[] nameAndValue = queryParam.split(PARAM_REGEX, -1);
            queryParams.put(nameAndValue[QUERY_PARAM_NAME_INDEX], nameAndValue[QUERY_PARAM_VALUE_INDEX]);
        }

        return queryParams;
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
