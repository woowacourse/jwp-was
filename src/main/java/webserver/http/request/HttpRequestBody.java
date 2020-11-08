package webserver.http.request;

import java.util.Map;

class HttpRequestBody {
    private final Map<String, String> data;

    public HttpRequestBody(Map<String, String> data) {
        this.data = data;
    }
}
