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

    public String getParameter(String key) {
        return requestParams.getOneParameterValue(key);
    }

    public List<String> getAllParameter(String key) {
        return requestParams.get(key);
    }

    public String getBody() {
        return body;
    }
}
