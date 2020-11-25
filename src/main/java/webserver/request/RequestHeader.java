package webserver.request;

import java.util.Collections;
import java.util.Map;

import utils.RequestUtils;

public class RequestHeader {
    private final Map<String, String> header;

    private RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public static RequestHeader of(String data) {
        return new RequestHeader(RequestUtils.getHeader(data));
    }

    public static RequestHeader empty() {
        return new RequestHeader(Collections.emptyMap());
    }

    public int getContentLength() {
        return Integer.parseInt(header.getOrDefault("Content-Length", "0"));
    }
}
