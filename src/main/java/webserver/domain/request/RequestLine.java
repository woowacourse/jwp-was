package webserver.domain.request;

import java.util.Map;

public class RequestLine {
    private final Method method;
    private final Uri uri;
    private final Protocol protocol;

    public RequestLine(Method method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine of(String source) {
        String[] sources = source.split(" ");
        Method method = Method.of(sources[0]);
        Uri uri = Uri.of(sources[1]);
        Protocol protocol = Protocol.of(sources[2]);

        return new RequestLine(method, uri, protocol);
    }

    public boolean isTemplatesResource() {
        return uri.isTemplatesResource();
    }

    public boolean isStaticResource() {
        return uri.isStaticResource();
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return uri.getPath();
    }

    public Map<String, String> getParameters() {
        return uri.getParameters();
    }
}
