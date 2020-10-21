package webserver.http.request;

import java.util.Collections;
import java.util.Set;

public class HttpRequestUrl {
    private static final String PATH_AND_PARAMETER_DIVIDER = "?";

    private final String requestUrl;
    private final HttpRequestUrlParameters httpRequestUrlParameters;

    public HttpRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        String parameterLine = requestUrl.substring(requestUrl.indexOf(PATH_AND_PARAMETER_DIVIDER) + 1);
        httpRequestUrlParameters = new HttpRequestUrlParameters(requestUrl, parameterLine);
    }

    public String getPath() {
        if (hasParameters()) {
            return requestUrl.substring(0, requestUrl.indexOf(PATH_AND_PARAMETER_DIVIDER));
        }
        return requestUrl;
    }

    public boolean isSamePath(String path) {
        return getPath().equals(path);
    }

    public boolean isStaticFile() {
        return StaticFiles.endsWith(requestUrl);
    }

    private boolean hasParameters() {
        return !httpRequestUrlParameters.isEmpty();
    }

    protected Set<String> getParameterKeySet() {
        return Collections.unmodifiableSet(httpRequestUrlParameters.getKeySet());
    }

    protected String getParameterValueBy(String key) {
        return httpRequestUrlParameters.getValueBy(key);
    }

    public String getContentType() {
        return StaticFiles.getContentType(requestUrl);
    }
}
