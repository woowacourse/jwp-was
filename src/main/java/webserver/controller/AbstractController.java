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

    //TODO 이 메서드가 여기 있어도 괜찮은가, 아니면 HttpRequest, HttpResponse와 각각 의존성을 맺는 게 더 좋을까?
    protected HttpSession getSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        //TODO SessionManager와 의존하는 부분을 이렇게 놔두어도 괜찮을까?
        String sessionId = httpRequest.getSessionId();
        if (checkSession(sessionId)) {
            sessionId = SessionManager.createSession();
        }
        //TODO 이 메서드에서 set까지 해버리는건 이상하지 않을까?
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
