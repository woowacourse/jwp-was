package web;

import static utils.HttpRequestParser.*;

import java.util.Map;
import java.util.Objects;

public class RequestUri {
    public static final int URI_INDEX = 0;
    public static final String PARAMETER_DELIMITER = "?";
    private static final String ROOT_PATH = "/";
    private static final int PARAMETER_INDEX = 1;
    private static final String INDEX_HTML = "/index.html";
    private static final String HTML_FILE = ".html";
    private static final String CSS_FILE = ".css";
    private static final String ICO_FILE = ".ico";
    private static final String JS_FILE = ".js";
    private String uri;
    private Map<String, String> parameters;

    public RequestUri(String uri) {
        this.uri = uri;
        this.parameters = parsingParameters();
    }

    public boolean isStaticFile() {
        return uri.contains(HTML_FILE) || uri.contains(CSS_FILE) || uri.contains(ICO_FILE)
                || uri.contains(JS_FILE);
    }

    public boolean isRootPath() {
        if (ROOT_PATH.equals(uri)) {
            uri = INDEX_HTML;
            return true;
        }
        return false;
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
}
