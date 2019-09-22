package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class HttpRequestStartLine {
    private static final String LINE_SPLITTER = " ";
    private static final String QUERY_SPLITTER = "\\?";
    private static final Logger log = LoggerFactory.getLogger(HttpRequestStartLine.class);

    private final HttpMethod httpMethod;
    private final String path;
    private final HttpRequestParameter httpRequestParameters;

    private HttpRequestStartLine(HttpMethod httpMethod, String path, HttpRequestParameter httpRequestParameters) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpRequestParameters = httpRequestParameters;
    }

    public static HttpRequestStartLine of(String startLine) throws UnsupportedEncodingException {
        String[] startLineValues = startLine.split(LINE_SPLITTER);

        HttpMethod httpMethod = HttpMethod.valueOf(startLineValues[0]);

        String url = startLineValues[1];
        String[] splittedUrl = url.split(QUERY_SPLITTER);

        String path = splittedUrl[0];
        HttpRequestParameter httpRequestParameters = parseHttpRequestParameter(splittedUrl);

        return new HttpRequestStartLine(httpMethod, path, httpRequestParameters);
    }

    private static HttpRequestParameter parseHttpRequestParameter(String[] splittedUrl) throws UnsupportedEncodingException {
        HttpRequestParameter httpRequestParameters = HttpRequestParameter.EMPTY_PARAMETER;
        if (1 < splittedUrl.length) {
            httpRequestParameters = HttpRequestParameter.of(splittedUrl[1]);
        }
        return httpRequestParameters;
    }

    HttpMethod getHttpMethod() {
        return httpMethod;
    }

    String getPath() {
        return path;
    }

    String getParameter(String key) {
        return httpRequestParameters.getParameter(key);
    }

    boolean hasParameters() {
        return !httpRequestParameters.equals(HttpRequestParameter.EMPTY_PARAMETER);
    }
}
