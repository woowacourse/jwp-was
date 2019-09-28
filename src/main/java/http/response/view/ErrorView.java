package http.response.view;

import com.google.common.collect.Maps;
import http.HTTP;
import http.response.ResponseStatus;

import java.util.Map;

public class ErrorView implements View {
    private final ResponseStatus responseStatus;
    private final Map<HTTP, String> header = Maps.newHashMap();
    private final byte[] body;

    public ErrorView(ResponseStatus responseStatus, String message) {
        this.responseStatus = responseStatus;
        this.body = message.getBytes();
    }

    @Override
    public ResponseStatus getResponseStatus() {
        return ResponseStatus.NOT_FOUND;
    }

    @Override
    public Map<HTTP, String> getHeader() {
        return header;
    }

    @Override
    public byte[] getBody() {
        return body;
    }
}
