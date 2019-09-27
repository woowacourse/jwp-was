package servlet.response;

import http.support.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class HttpServletResponse {
    private String resourcePath = "";
    private StatusCode statusCode = StatusCode.NOT_FOUND;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> model = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();

    public HttpServletResponse() {
    }

    public HttpServletResponse(final String path) {
        this.resourcePath = path;
    }

    public void forward(final String path) {
        this.resourcePath = path;
        this.statusCode = StatusCode.OK;
    }

    public void sendRedirect(String path) {
        this.resourcePath = path;
        this.statusCode = StatusCode.FOUND;
    }

    public void addParameter(final String key, final Object value) {
        model.put(key, value);
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public void addCookie(final String key, final String cookie) {
        cookies.put(key, cookie);
    }

    public String getResourcePath() {
        return null;
    }
}
