package web.request;

public class RequestUri {
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    private static final String URI_DELIMITER = " ";
    private static final int URI_SIZE = 3;

    private final HttpMethod method;
    private final RequestPath path;
    private final String protocol;

    public RequestUri(String uri) {
        if (uri.isEmpty()) {
            throw new IllegalArgumentException("요청 URI가 비어있습니다.");
        }

        String[] splitUri = uri.split(URI_DELIMITER);
        if (splitUri.length != URI_SIZE) {
            throw new IllegalArgumentException(String.format("%s : 매개변수가 올바르지 않습니다.", uri));
        }
        this.method = HttpMethod.valueOf(splitUri[METHOD_INDEX]);
        this.path = new RequestPath(splitUri[PATH_INDEX]);
        this.protocol = splitUri[PROTOCOL_INDEX];
    }

    public String getParam(final String key) {
        return this.path.getRequestParameter(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestPath() {
        return path.getRequestPath();
    }

    public String getProtocol() {
        return protocol;
    }
}