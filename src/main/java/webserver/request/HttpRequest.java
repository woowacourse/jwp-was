package webserver.request;

import webserver.controller.RequestMapping;
import webserver.storage.Cookie;
import webserver.storage.HttpSession;
import webserver.storage.SessionManager;

import java.util.List;

public class HttpRequest {
    private static final String BLANK = "";
    private static final String COLON = ":";
    private static final String COOKIE = "Cookie";
    private static final String JSESSIONID = "JSESSIONID";

    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestData requestParams;
    private RequestData requestBody;
    private Cookie cookie;
    private String sessionId;

    public HttpRequest(List<String> lines) {
        this.requestLine = new RequestLine(lines.get(0));
        this.requestParams = getRequestParams(requestLine);
        this.requestHeaders = new RequestHeaders();
        this.requestBody = new RequestData();
        setRequestHeaderAndBody(lines);
        this.cookie = new Cookie(requestHeaders.get(COOKIE));
        this.sessionId = cookie.get(JSESSIONID);
    }

    private static RequestData getRequestParams(RequestLine requestLine) {
        RequestData requestParams = new RequestData();

        if (requestLine.hasParams()) {
            String search = requestLine.getQueryString();
            requestParams.put(search);
        }
        return requestParams;
    }

    private void setRequestHeaderAndBody(List<String> lines) {
        int index = 0;
        String line;
        while (!BLANK.equals(line = lines.get(++index))) {
            buildRequestHeader(line);
        }

        if (requestHeaders.containsKey("Content-Length")) {
            buildRequestBody(lines.get(index + 1));
        }
    }

    private void buildRequestHeader(String line) {
        String[] splitHeader = line.split(COLON, 2);
        requestHeaders.put(splitHeader[0].trim(), splitHeader[1].trim());
    }

    private void buildRequestBody(String bodyLine) {
        requestBody.put(bodyLine);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public RequestMapping getRequestMapping() {
        return new RequestMapping(Method.valueOf(getMethod()), getPath());
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public String getParam(String key) {
        if (requestParams.isEmpty()) {
            throw new IllegalArgumentException("Parameter 가 없습니다.");
        }
        return requestParams.get(key);
    }

    public HttpSession getSession() {
        HttpSession session = SessionManager.getInstance().getSession(sessionId);
        if (!session.isSameId(sessionId)) {
            this.sessionId = session.getId();
        }
        return session;
    }

    public String getBody(String key) {
        return requestBody.get(key);
    }

    public String getPath() {
        return requestLine.getUri();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", requestParams=" + requestParams +
                ", requestBody=" + requestBody +
                ", cookie=" + cookie +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
