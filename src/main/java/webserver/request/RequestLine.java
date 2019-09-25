package webserver.request;

import java.net.URISyntaxException;
import java.util.Objects;

public class RequestLine {
    private final RequestMethod method;
    private final RequestUri uri;

    public RequestLine(String method, String uri) throws URISyntaxException {
        this.method = RequestMethod.valueOf(method);
        this.uri = new RequestUri(uri);
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getQueryString(String key) {
        return uri.getQueryString(key);
    }

    public String getAbsPath() {
        return uri.getAbsPath();
    }

    public boolean isFile() {
        return uri.isFile();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestLine that = (RequestLine) o;

        if (method != that.method) return false;
        return Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
