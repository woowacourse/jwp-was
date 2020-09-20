package webserver;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpStartLine {
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
        String[] startLine = line.split(" ");
        MethodType method = MethodType.of(startLine[0]);
        String path = getPathWithoutParams(startLine[1]);
        String protocolVersion = startLine[2];
        Map<String, String> queryParams = createParams(startLine[1]);

        return new HttpStartLine(method, path, protocolVersion, new QueryParams(queryParams));
    }

    private static Map<String, String> createParams(String path) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        if (!path.contains("?")) {
            return queryParams;
        }
        String queryString = path.split("\\?")[1];
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] attribute = param.split("=");
            queryParams.put(attribute[0], attribute[1]);
        }
        return queryParams;
    }

    private static String getPathWithoutParams(String path) {
        return path.split("\\?")[0];
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
