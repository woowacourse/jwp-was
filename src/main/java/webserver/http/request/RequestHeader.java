package webserver.http.request;

import java.util.Map;

public class RequestHeader {
    private Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "header=" + header +
                '}';
    }
}
