package kr.wootecat.dongle.model.http.request;

public class HttpRequestBody {

    private final QueryParameters queryParameters;

    public HttpRequestBody(QueryParameters queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static HttpRequestBody empty() {
        return new HttpRequestBody(QueryParameters.empty());
    }

    public static HttpRequestBody from(String body) {
        if (body.isEmpty()) {
            return empty();
        }
        return new HttpRequestBody(QueryParameters.from(body));
    }

    public String get(String name) {
        return queryParameters.get(name);
    }
}
