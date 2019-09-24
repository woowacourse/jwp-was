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

    public Map<HTTP, String> getHeader() {
        return header;
    }

    ;
}
