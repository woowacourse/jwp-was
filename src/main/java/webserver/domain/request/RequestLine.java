package webserver.domain.request;

import java.util.Map;

public class RequestLine {
    private final Method method;
    private final Uri uri;
    private final HttpVersion version;

    public RequestLine(Method method, Uri uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine of(String source) {
        String[] sources = source.split(" ");
        Method method = Method.of(sources[0]);
        Uri uri = Uri.of(sources[1]);
        HttpVersion httpVersion = HttpVersion.of(sources[2]);

        return new RequestLine(method, uri, httpVersion);
    }

    public boolean isTemplatesResource() {
        return uri.isTemplatesResource();
    }

    public boolean isStaticResource() {
        return uri.isStaticResource();
    }

    public String getPath() {
        return uri.getPath();
    }

    public Map<String, String> getParameters() {
        return uri.getParameters();
    }
}
