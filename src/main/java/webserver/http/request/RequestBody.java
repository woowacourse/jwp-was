package webserver.http.request;

import java.util.List;

public class RequestBody {
    private String body;
    private RequestParams requestParams;

    public RequestBody(String body, RequestParams requestParams) {
        this.body = body;
        this.requestParams = requestParams;
    }

    public static RequestBody from(String query) {
        return new RequestBody(query, RequestParams.from(query));
    }

    public List<String> getParameters(String key) {
        return requestParams.getParameters(key);
    }

    public String getBody() {
        return body;
    }
}
