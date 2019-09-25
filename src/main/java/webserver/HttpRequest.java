package webserver;

import java.util.Map;

public class HttpRequest {

    public static final String SID_ATTR_KEY = "sid";

    private final HttpMethod method;
    private final String url;
    private final String path;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final Map<String, ?> body;
    private HttpSession session;

    HttpRequest(HttpMethod method, String url, String path, Map<String, String> queries,
                Map<String, String> headers, Map<String, String> cookies, Map<String, ?> body) {
        this.method = method;
        this.url = url;
        this.path = path;
        this.queries = queries;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;

        if (cookies.containsKey(SID_ATTR_KEY)) {
            session = SessionManager.getSession(cookies.get(SID_ATTR_KEY));
        }
    }

    public boolean matchMethod(HttpMethod method) {
        return this.method.equals(method);
    }

    public HttpSession getSession() {
        if (session == null) {
            session = SessionManager.getSession(cookies.get(SID_ATTR_KEY));
        }
        return session;
    }

    public boolean hasValidSession() {
        return session != null && !session.isInvalid();
    }

    public String getQuery(String key) {
        return queries.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public Map<String, ?> getBody() {
        return body;
    }
}
