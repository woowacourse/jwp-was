package servlet.response;

import http.support.StatusCode;

import java.util.*;

public class HttpServletResponse {
    private String resourcePath = "/";
    private StatusCode statusCode = StatusCode.NOT_FOUND;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> model = new HashMap<>();

    //@TODO 또 다른 객체로 뺄 것
    private List<String> cookies = new ArrayList<>();

    public HttpServletResponse() {
    }

    public void forward(final String path) {
        this.resourcePath = path;
        this.statusCode = StatusCode.OK;
    }

    public void sendRedirect(final String path) {
        headers.put("Location", path);
        this.statusCode = StatusCode.FOUND;
    }

    public void addParameter(final String key, final Object value) {
        model.put(key, value);
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public void addCookie(final String key, final String cookie) {
        cookies.add(key + "=" + cookie);
    }

    public String getCookie() {
        return String.join("; ", cookies);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
