package http.request;

import http.parameter.Parameters;

public class HttpRequestStartLine {
    private static final String LINE_SPLITTER = " ";
    private static final String QUERY_SPLITTER = "\\?";

    private final HttpMethod httpMethod;
    private final String path;
    private final Parameters parameters;

    public HttpRequestStartLine(HttpMethod httpMethod, String path, Parameters parameters) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.parameters = parameters;
    }

    public static HttpRequestStartLine of(String startLine) {
        String[] startLineValues = startLine.split(LINE_SPLITTER);
        if (startLineValues.length != 3) {
            throw InvalidHttpRequestStartLine.of(startLine);
        }

        String url = startLineValues[1];
        String[] splittedUrl = url.split(QUERY_SPLITTER);

        HttpMethod httpMethod = HttpMethod.fromMethodName(startLineValues[0]);
        String path = getPath(splittedUrl);
        Parameters parameters = getParameters(splittedUrl);

        return new HttpRequestStartLine(httpMethod, path, parameters);
    }


    private static String getPath(String[] splittedUrl) {
        return splittedUrl[0];
    }

    private static Parameters getParameters(String[] splittedUrl) {
        if (splittedUrl.length < 2) {
            return Parameters.EMPTY_PARAMETERS;
        }
        return Parameters.fromQueryString(splittedUrl[1]);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
