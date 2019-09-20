package http.request;

import java.util.List;

public class HttpRequestBody {
    List<String> body;

    public HttpRequestBody(List<String> body) {
        this.body = body;
    }

    public List<String> getBody() {
        return body;
    }
}
