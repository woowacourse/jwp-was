package web.request;

public class RequestBody {
    private final UrlParameters parameters;
    private final String body;

    public RequestBody(String body) {
        this.body = body;
        this.parameters = new UrlParameters(this.body);
    }

    public String getBody() {
        return body;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
