package http.request;

import http.SessionContainer;
import http.response.HttpResponse;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

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
        if (isNotExistSession(sessionContainer)) {
            String sessionId = sessionContainer.createSession();
            httpResponse.putHeader(HttpResponse.SET_COOKIE,
                    String.format("%s=%s", SessionContainer.SESSION_KEY_FOR_COOKIE, sessionId));
        }
    }

    private boolean isNotExistSession(SessionContainer sessionContainer) {
        String sessionId = cookie.get(SessionContainer.SESSION_KEY_FOR_COOKIE);
        return StringUtils.isEmpty(sessionId)
                || sessionContainer.getSession(sessionId) == null;
    }

    public boolean containsKeyInCookie(String key) {
        return cookie.containsKey(key);
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }
}
