package http.request;

public class RequestUri {
    private String path;
    private QueryParams queryParams;

    public RequestUri(String requestUri) {
        String[] pathAndQueryParams = requestUri.split("\\?");
        this.path = pathAndQueryParams[0];
        if (pathAndQueryParams.length >= 2) {
            this.queryParams = new QueryParams(pathAndQueryParams[1]);
        } else {
            this.queryParams = new QueryParams();
        }
    }

    public String getPath() {
        return path;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    @Override
    public String toString() {
        return path + queryParams.toString();
    }
}
