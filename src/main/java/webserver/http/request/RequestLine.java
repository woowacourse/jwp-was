package webserver.http.request;

import webserver.http.HttpVersion;

public class RequestLine {
    private static final String DELIMITER_FOR_CREATE = " ";
    private static final String DELIMITER_URL_TO_PATH_AND_PARAMETERS = "?";
    private static final int BOUNDARY_INDEX_FOR_PARAMETERS = 0;
    private static final String DEFAULT_PARAMETERS = "";

    private final HttpMethod method;
    private final String url;
    private final HttpVersion httpVersion;
    private final String path;
    private final String parameters;

    public RequestLine(final String requestLine) {
        final String[] split = requestLine.split(DELIMITER_FOR_CREATE);

        this.method = HttpMethod.of(split[0]);
        this.url = split[1];
        this.httpVersion = HttpVersion.of(split[2]);

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
