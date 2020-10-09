package http.request;

import http.response.HttpResponse;
import http.servlet.HttpSession;
import http.servlet.SessionContainer;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestParams requestParams;
    private final Cookie cookie;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestParams requestParams, Cookie cookie) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestParams = requestParams;
        this.cookie = cookie;
    }

    public boolean isMatchMethod(HttpMethod method) {
        return method.equals(requestLine.getMethod());
    }

    public boolean isNotFound() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getPath());
        return body.length == 0;
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public String getParameter(String key) {
        return requestParams.get(key);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public void sessionCheck(HttpResponse httpResponse, SessionContainer sessionContainer) {
        if (!cookie.containsKey(SessionContainer.SESSION_KEY_FOR_COOKIE)) {
            String sessionId = String.valueOf(UUID.randomUUID());
            sessionContainer.put(sessionId, new HttpSession(sessionId));
            httpResponse.putHeader(HttpResponse.SET_COOKIE,
                    String.format("%s=%s", SessionContainer.SESSION_KEY_FOR_COOKIE, sessionId));
        }
    }

    public boolean containsKeyInCookie(String key) {
        return cookie.containsKey(key);
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }
}
