package webserver.request;

import java.util.Map;

public class HttpStartLine {
    private static final String SPLIT_DELIMITER = " ";
    private static final String QUERY_PARAM_DELIMITER = "\\?";

    private final MethodType method;
    private final String path;
    private final String protocolVersion;
    private final QueryParams queryParams;

    private HttpStartLine(final MethodType method, final String path, final String protocolVersion,
        final QueryParams queryParams) {
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
        this.queryParams = queryParams;
    }

    public static HttpStartLine of(final String line) {
        String[] startLine = line.split(SPLIT_DELIMITER);
        MethodType method = MethodType.of(startLine[0]);
        String path = getPathWithoutParams(startLine[1]);
        String protocolVersion = startLine[2];
        QueryParams queryParams = QueryParams.of(startLine[1]);

        return new HttpStartLine(method, path, protocolVersion, queryParams);
    }

    private static String getPathWithoutParams(String path) {
        return path.split(QUERY_PARAM_DELIMITER)[0];
    }

    public String getPath() {
        return path;
    }

    public MethodType getMethod() {
        return method;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public Map<String, String> getQueryParams() {
        return queryParams.getQueryParams();
    }

    public String getParam(final String key) {
        return queryParams.getParam(key);
    }
}
