package http.request;

import http.common.Cookie;
import http.common.HeaderFields;
import http.common.HttpSession;
import http.exception.InvalidHeaderException;
import webserver.SessionHandler;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HeaderFields headerFields;
    private final RequestBody datas;

    public HttpRequest(RequestLine requestLine, HeaderFields headerFields, RequestBody datas) {
        this.requestLine = requestLine;
        this.headerFields = headerFields;
        this.datas = datas;
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }

    public String getData(String fieldName) {
        if (requestLine.isExistValue(fieldName)) {
            return requestLine.getQueryString(fieldName);
        }
        return datas.getData(fieldName);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Cookie getCookie(String name) {
        return headerFields.getCookie(name);
    }

    public HttpSession getSession() {
        try {
            Cookie sessionCookie = headerFields.getCookie("sessionId");
            return SessionHandler.getInstance().getSession(sessionCookie.getValue());
        } catch (InvalidHeaderException e) {
            HttpSession session = new HttpSession();
            SessionHandler.getInstance().addSession(session.getId(), session);
            return session;
        }
    }
}
