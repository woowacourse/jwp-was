package http.request;

import java.util.Arrays;
import java.util.List;

public class HttpStartLine {
    private static final String DELIMITER = " ";
    private static final int INDEX_OF_METHOD = 0;
    private static final int INDEX_OF_URL = 1;
    private static final int INDEX_OF_VERSION = 2;

    private final String method;
    private final RequestUrl requestUrl;
    private final String version;

    public HttpStartLine(final String httpRequestLine) {
        List<String> lines = Arrays.asList(httpRequestLine.split(DELIMITER));

        this.method = lines.get(INDEX_OF_METHOD);
        this.requestUrl = new RequestUrl(lines.get(INDEX_OF_URL));
        this.version = lines.get(INDEX_OF_VERSION);
    }

    public String getMethod() {
        return method;
    }

    public String getResourcePath() {
        return requestUrl.getResourcePath();
    }

    public String getVersion() {
        return version;
    }

    public String getParameter(String key) {
        return requestUrl.getQueryParameter(key);
    }
}
