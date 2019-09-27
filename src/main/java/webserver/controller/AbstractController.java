package webserver.controller;

import webserver.Controller;
import webserver.SessionManager;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public abstract class AbstractController implements Controller {
    public static final String REDIRECT_VIEW = "/redirect:";
    public static final String ERROR_VIEW = "/error:";

    @Override
    public String service(HttpRequest request, HttpResponse response) {
        if (request.checkMethod(HttpMethod.GET)) {
            return doGet(request, response);
        }

        if (request.checkMethod(HttpMethod.POST)) {
            return doPost(request, response);
        }

        throw new NotSupportedHttpMethodException();
    }

    protected String doGet(HttpRequest httpRequest, HttpResponse response) {
        throw new NotSupportedHttpMethodException();
    }

    protected String doPost(HttpRequest httpRequest, HttpResponse response) {
        throw new NotSupportedHttpMethodException();
    }

    protected HttpSession getSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        String sessionId = httpRequest.getSessionId();
        if (checkSession(sessionId)) {
            sessionId = SessionManager.createSession();
        }
        httpResponse.setSession(sessionId);
        return SessionManager.getSession(sessionId);
    }

    private boolean checkSession(String sessionId) {
        return hasSession(sessionId) || validateSession(sessionId);
    }

    private boolean hasSession(String sessionId) {
        return sessionId == null;
    }

    private boolean validateSession(String sessionId) {
        return SessionManager.getSession(sessionId) == null;
    }

    protected void setSession(HttpRequest httpRequest, HttpResponse httpResponse, String name, Object value) {
        HttpSession session = getSession(httpRequest, httpResponse);
        session.setAttributes(name, value);
    }
}
