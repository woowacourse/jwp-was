package webserver;

import session.HttpSession;
import session.HttpSessionManager;

import java.util.Map;

public class Request {

    public static final String SESSION_COOKIE_KEY = "SID";

    private final RequestMapping requestMapping;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final byte[] body;
    private HttpSession session;

    public Request(RequestMapping requestMapping, Map<String, String> queries, Map<String, String> headers, Map<String, String> cookies, byte[] body) {
        this.requestMapping = requestMapping;
        this.queries = queries;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
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
        if (session != null) {
            return session;
        }

        String sessionId = cookies.get(SESSION_COOKIE_KEY);
        if (HttpSessionManager.isMappingValidSession(sessionId)) {
            return HttpSessionManager.getSession(sessionId);
        }

        return HttpSessionManager.createSession();
    }
}
