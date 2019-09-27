package http.response.view;

import com.google.common.collect.Maps;
import http.HTTP;
import http.response.ResponseStatus;

import java.util.Map;

public class RedirectView implements View {
    private final Map<HTTP, String> header = Maps.newHashMap();

    public RedirectView(String path) {
        header.put(HTTP.LOCATION, path);
    }

    @Override
    public ResponseStatus getResponseStatus() {
        return ResponseStatus.FOUND;
    }

    @Override
    public Map<HTTP, String> getHeader() {
        return header;
    }

    @Override
    public byte[] getBody() {
        return "".getBytes();
    }
}
