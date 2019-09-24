package http.request;

public class HttpUri {
    private String path;
    private QueryParameter query;

    private HttpUri(String path, QueryParameter query) {
        this.path = path;
        this.query = query;
    }

    public static HttpUri of(String requestUri) {
        if (requestUri.contains("\\?")) {
            String[] tokens = requestUri.split("\\?");
            String path = tokens[0];
            QueryParameter query = QueryParameter.of(tokens[1]);

            return new HttpUri(path, query);
        }
        return new HttpUri(requestUri, QueryParameter.empty());
    }

    public String getPath() {
        return path;
    }
}
