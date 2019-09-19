package http.request;

import http.common.URL;

public class RequestLine {
    private final RequestMethod method;
    private final URL url;
    private final String version;

    public RequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        method = RequestMethod.valueOf(tokens[0]);
        url = URL.of(tokens[1]);
        version = tokens[2];
    }

    public RequestMethod getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }
}
