package webserver.http.request;

public class RequestLine {
    private static final String DELIMITER_URL_TO_PATH_AND_PARAMETERS = "?";
    private static final int BOUNDARY_INDEX_FOR_PARAMETERS = 0;
    private static final String DEFAULT_PARAMETERS = "";

    private final HttpMethod method;
    private final String url;
    private final HttpVersion httpVersion;
    private final String path;
    private final String parameters;

    private RequestLine(final HttpMethod method, final String url, final HttpVersion httpVersion) {
        this.method = method;
        this.url = url;
        this.httpVersion = httpVersion;
        final int index = url.indexOf(DELIMITER_URL_TO_PATH_AND_PARAMETERS);
        this.path = validPath(url, index);
        this.parameters = validParameters(url, index);
    }

    private String validPath(final String url, final int index) {
        return index > BOUNDARY_INDEX_FOR_PARAMETERS ? url.substring(0, index) : url;
    }

    private String validParameters(final String url, final int index) {
        return index > BOUNDARY_INDEX_FOR_PARAMETERS ? url.substring(index + 1) : DEFAULT_PARAMETERS;
    }

    static RequestLine of(final HttpMethod method, final String url, final HttpVersion httpVersion) {
        return new RequestLine(method, url, httpVersion);
    }

    HttpMethod getMethod() {
        return method;
    }

    String getUrl() {
        return url;
    }

    String getPath() {
        return path;
    }

    String getParameters() {
        return parameters;
    }

    HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
