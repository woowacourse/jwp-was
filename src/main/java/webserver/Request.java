package webserver;

import java.util.Map;

public class Request {

    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final byte[] body;

    public Request(String method, String url, Map<String, String> headers, byte[] body) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        if (body == null) {
            return String.format("%s %s\nEmpty body", method, url);
        }
        return String.format("%s %s\n%s", method, url, new String(body));
    }
}
