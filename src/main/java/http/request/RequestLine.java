package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class RequestLine {
    private static final String SPACE = " ";

    private RequestMethod method;
    private RequestUri requestUri;
    private String httpVersion;

    public RequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        Objects.requireNonNull(requestLine);
        String[] tokens = requestLine.split(SPACE);
        this.method = RequestMethod.valueOf(tokens[0]);
        this.requestUri = new RequestUri(tokens[1]);
        this.httpVersion = tokens[2];
    }

    public RequestMethod getMethod() {
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
        return method + SPACE + requestUri.toString() + SPACE + httpVersion;
    }
}
