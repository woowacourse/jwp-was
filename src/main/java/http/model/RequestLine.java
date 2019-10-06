package http.model;

public class RequestLine {
    private static final String SEPARATOR = " ";

    private final HttpMethod httpMethod;
    private final HttpProtocols httpProtocols;
    private final HttpUri httpUri;

    public RequestLine(String message) {
        String[] tokens = message.split(SEPARATOR);
        httpMethod = HttpMethod.of(tokens[0]);
        httpUri = new HttpUri(tokens[1]);
        httpProtocols = HttpProtocols.of(tokens[2]);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpProtocols getHttpProtocols() {
        return httpProtocols;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }
}
