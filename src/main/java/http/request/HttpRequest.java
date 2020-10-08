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

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestParams requestParams) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestParams = requestParams;
    }

    public boolean isMatchMethod(HttpMethod method) {
        return method.equals(requestLine.getMethod());
    }

    public boolean isNotFound() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getPath());
        return body.length == 0;
    }

    public boolean isNotLogined() {
        return !requestHeader.isLogined();
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

    public boolean notContainsSessionId() {
        return !requestHeader.containsSessionId();
    }

    public void sessionCheck(HttpResponse httpResponse, SessionContainer sessionContainer) {
        if (notContainsSessionId()) {
            String sessionId = String.valueOf(UUID.randomUUID());
            sessionContainer.put(sessionId, new HttpSession(sessionId));
            httpResponse.putHeader(HttpResponse.SET_COOKIE,
                    String.format("%s=%s", SessionContainer.SESSION_KEY_FOR_COOKIE, sessionId));
        }
    }
}
