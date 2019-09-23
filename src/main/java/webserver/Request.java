package webserver;

import org.springframework.http.HttpMethod;
import session.HttpSession;
import session.HttpSessionManager;

import java.util.Map;

public class Request {

    public static final String SESSION_COOKIE_KEY = "SID";

    private final String method;
    private final String path;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final byte[] body;
    private HttpSession session;

    public Request(String method, String path, Map<String, String> queries, Map<String, String> headers, Map<String, String> cookies, byte[] body) {
        this.method = method;
        this.path = path;
        this.queries = queries;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public boolean matchMethod(HttpMethod httpMethod) {
        return httpMethod.matches(method);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
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

    public byte[] getBody() {
        return body;
    }

    public HttpSession getSession() {
        if (session == null) {
            session = getValidSession();
        }

        return session;
    }

    private HttpSession getValidSession() {
        HttpSession currentSession = HttpSessionManager.getSession(cookies.get(SESSION_COOKIE_KEY));
        if (currentSession != null && currentSession.isValid()) {
            return session;
        }

        return createNewSession();
    }

    private HttpSession createNewSession() {
        HttpSession newSession = HttpSession.create();
        HttpSessionManager.addSession(newSession);
        return newSession;
    }
}
