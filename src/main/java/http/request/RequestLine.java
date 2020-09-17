package http.request;

import java.util.Objects;

import http.HttpVersion;

public class RequestLine {
    private static final String DELIMITER = " ";

    private HttpMethod httpMethod;
    private RequestUri requestUri;
    private HttpVersion httpVersion;

    private RequestLine(HttpMethod httpMethod, RequestUri requestUri,
            HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine from(String requestLine) {
        Objects.requireNonNull(requestLine);
        String[] token = requestLine.split(DELIMITER);

        return new RequestLine(HttpMethod.valueOf(token[0]), RequestUri.from(token[1]),
                HttpVersion.from(token[2]));
    }

    public boolean isPost() {
        return httpMethod.isPost();
    }

    public String getPath() {
        return requestUri.getPath();
    }
}
