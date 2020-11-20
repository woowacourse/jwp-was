package server.web.request;

public class RequestPath {
    private final String fullPath;
    private final String requestPath;
    private final UrlParameters urlParameters;

    public RequestPath(String requestPath) {
        this.fullPath = requestPath;
        String[] splitPath = requestPath.split("\\?");
        this.requestPath = splitPath[0];

        if (hasNoParameters()) {
            this.urlParameters = UrlParameters.empty();
            return;
        }
        String body = splitPath[1];
        this.urlParameters = new UrlParameters(body);
    }

    private boolean hasNoParameters() {
        return this.fullPath.equals(this.requestPath);
    }

    public String getRequestPath() {
        return this.requestPath;
    }

    public String getRequestParameter(String key) {
        return urlParameters.get(key);
    }
}
