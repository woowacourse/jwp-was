package webserver.request;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader header;
    private RequestCookie cookie;
    private RequestBody body;
    private Map<String, Object> attribute;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestCookie cookie, RequestBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.cookie = cookie;
        this.body = body;
        attribute = new HashMap<>();
    }

    public String getAbsPath() {
        return requestLine.getAbsPath();
    }

    public boolean isAcceptContainsHtml() {
        return header.getHeader("Accept").contains("text/html");
    }

    public String getParam(String key) {
        return requestLine.getQueryString(key);
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody(String key) {
        return body.getBody(key);
    }

    public String getCookie(String key) {
        return cookie.getCookie(key);
    }

    public boolean isFile() {
        return requestLine.isFile();
    }

    public void setAttribute(String key, Object value) {
        attribute.put(key, value);
    }

    public Object getAttribute(String key) {
        return attribute.get(key);
    }
}
