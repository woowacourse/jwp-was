package http.request;

import java.io.UnsupportedEncodingException;

public class HttpRequestStartLine {
    private static final String LINE_SPLITTER = " ";
    private static final String QUERY_SPLITTER = "\\?";

    private final HttpMethodType httpMethodType;
    private final String path;
    private final HttpRequestParameter httpRequestParameters;

    private HttpRequestStartLine(HttpMethodType httpMethodType, String path, HttpRequestParameter httpRequestParameters) {
        this.httpMethodType = httpMethodType;
        this.path = path;
        this.httpRequestParameters = httpRequestParameters;
    }

    public static HttpRequestStartLine of(String startLine) throws UnsupportedEncodingException {
        String[] startLineValues = startLine.split(LINE_SPLITTER);

        HttpMethodType httpMethodType = HttpMethodType.valueOf(startLineValues[0]);

        String url = startLineValues[1];
        String[] splittedUrl = url.split(QUERY_SPLITTER);

        String path = splittedUrl[0];
        HttpRequestParameter httpRequestParameters = parseHttpRequestParameter(splittedUrl);

        return new HttpRequestStartLine(httpMethodType, path, httpRequestParameters);
    }

    private static HttpRequestParameter parseHttpRequestParameter(String[] splittedUrl) throws UnsupportedEncodingException {
        HttpRequestParameter httpRequestParameters = HttpRequestParameter.EMPTY_PARAMETER;
        if (1 < splittedUrl.length) {
            httpRequestParameters = HttpRequestParameter.of(splittedUrl[1]);
        }
        return httpRequestParameters;
    }

    HttpMethodType getHttpMethodType() {
        return httpMethodType;
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
