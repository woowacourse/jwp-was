package http.request;

import java.util.Objects;

import http.HttpMethod;
import http.URI;

public class StartLine {
    private static final String START_LINE_SPLITTER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;

    private HttpMethod httpMethod;
    private URI uri;
    private String version;

    public static StartLine from(String firstLine) {
        if (Objects.isNull(firstLine) || firstLine.isEmpty()) {
            throw new IllegalArgumentException("잘못된 생성 요청입니다.");
        }
        String[] tokens = firstLine.split(START_LINE_SPLITTER);
        HttpMethod httpMethod = HttpMethod.from(tokens[HTTP_METHOD_INDEX]);
        URI uri = URI.from(tokens[URI_INDEX]);
        return new StartLine(httpMethod, uri, tokens[HTTP_VERSION_INDEX]);
    }

    private StartLine(HttpMethod httpMethod, URI uri, String version) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.version = version;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return uri.getRawPath();
    }

    public String getQuery() {
        return uri.getRawQuery();
    }

    public String getVersion() {
        return version;
    }
}
