package http.request;

import java.io.UnsupportedEncodingException;

public class HttpRequestStartLine {
    private static final String LINE_SPLITTER = " ";
    private static final String QUERY_SPLITTER = "\\?";

    private final HttpMethod httpMethod;
    private final String path;
    private final HttpRequestParameter httpRequestParameters;

    private HttpRequestStartLine(HttpMethod httpMethod, String path, HttpRequestParameter httpRequestParameters) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpRequestParameters = httpRequestParameters;
    }

    public static HttpRequestStartLine of(String startLine) throws UnsupportedEncodingException {
        String[] startlineValues = startLine.split(LINE_SPLITTER);

        HttpMethod httpMethod = HttpMethod.valueOf(startlineValues[0]);

        String url = startlineValues[1];
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

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return httpRequestParameters.getParameter(key);
    }
}
