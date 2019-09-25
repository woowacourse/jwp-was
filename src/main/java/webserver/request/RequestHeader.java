package webserver.request;

import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> header;
    private Map<String, String> cookie;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
        this.cookie = generateCookie();
    }

    private Map<String, String> generateCookie() {
        String rowCookie = header.get("Cookie");
        if (rowCookie != null) {
            return HttpRequestUtils.parseParamToMap(rowCookie, "; ");
        }
        return new HashMap<>();
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
            "header=" + header +
            '}';
    }
}
