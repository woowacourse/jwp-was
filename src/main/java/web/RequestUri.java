package web;

import static utils.HttpRequestParser.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestUri {
    public static final int URI_INDEX = 0;
    public static final String PARAMETER_DELIMITER = "?";
    static final String INDEX_HTML = "/index.html";
    private static final int PARAMETER_INDEX = 1;

    private String uri;
    private Map<String, String> parameters;

    public RequestUri(String uri) {
        this.uri = uri;
        this.parameters = parsingParameters();
    }

    public boolean isStaticFile() {
        return findResource().isPresent();
    }

    public Optional<ResourceMatcher> findResource() {
        return ResourceMatcher.fromUri(uri);
    }

    public String findPath() {
        if (findResource().isPresent()) {
            return findResource().get().getResourcePath();
        }
        return EMPTY;
    }

    public String findContentType() {
        if (findResource().isPresent()) {
            return findResource().get().getContentType();
        }
        return "*/*";
    }

    private Map<String, String> parsingParameters() {
        if (!uri.contains(PARAMETER_DELIMITER)) {
            return null;
        }
        String[] requestUri = uri.split("\\" + PARAMETER_DELIMITER);
        uri = requestUri[URI_INDEX];
        return parsingData(requestUri[PARAMETER_INDEX]);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestUri that = (RequestUri)o;
        return Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }

    @Override
    public String toString() {
        return "RequestUri{" +
                "uri='" + uri + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
