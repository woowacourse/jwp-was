package http.response.view;

import http.HTTP;
import http.response.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class View {
    final Map<HTTP, String> header = new HashMap<>();
    byte[] body;
    ResponseStatus responseStatus;

    View(ResponseStatus responseStatus) {
        this.body = "".getBytes();
        this.responseStatus = responseStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void addHeader(HTTP key, String value) {
        header.put(key, value);
    }

    public boolean checkHeader(HTTP http) {
        return header.containsKey(http);
    }

    public String getHeaderContents(HTTP http) {
        return header.getOrDefault(http, "");
    }
}
