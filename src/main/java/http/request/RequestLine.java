package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

import http.HttpMethod;

public class RequestLine {
    private static final String SEPARATOR = " ";

    private HttpMethod method;
    private RequestUri requestUri;
    private String httpVersion;

    public RequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        Objects.requireNonNull(requestLine);
        String[] tokens = requestLine.split(SEPARATOR);
        this.method = HttpMethod.valueOf(tokens[0]);
        this.requestUri = new RequestUri(tokens[1]);
        this.httpVersion = tokens[2];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public QueryParams getQueryParams() {
        return requestUri.getQueryParams();
    }

    public String getPath() {
        return requestUri.getPath();
    }

    @Override
    public String toString() {
        return method + SEPARATOR + requestUri.toString() + SEPARATOR + httpVersion;
    }
}
