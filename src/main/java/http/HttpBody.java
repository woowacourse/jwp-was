package http;

public class HttpBody {
    private static final String NEW_LINE = "\n";

    private RequestParameter requestBody;
    private String body;

    public HttpBody(RequestParameter requestBody, String body) {
        this.requestBody = requestBody;
        this.body = body;
    }

    public HttpBody(String body) {
        this(null, body);
    }

    public String getParameter(String key) {
        return requestBody.getParameter(key);
    }

    public String getBody() {
        return body;
    }
}