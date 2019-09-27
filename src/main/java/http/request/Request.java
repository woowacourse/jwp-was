package http.request;

import controller.controllermapper.ControllerMapper;
import http.session.Cookie;
import http.session.Session;
import http.session.SessionRepository;

public class Request {
    private RequestMethod requestMethod;
    private RequestUrl url;
    private QueryParameters queryParameters;
    private Cookie cookie;
    private RequestInformation requestInformation;

    public Request(RequestMethod requestMethod, RequestUrl url, RequestInformation requestInformation) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.queryParameters = requestInformation.createQueryParametes();
        this.cookie = requestInformation.createCookie();
        this.requestInformation = requestInformation;
    }

    public ControllerMapper createControllerMapper() {
        return new ControllerMapper(requestMethod, url.getOriginalUrlPath());
    }

    public RequestUrl getUrl() {
        return url;
    }


    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public Session getSession() {
        String sessionId = this.cookie.getSessionId();
        return SessionRepository.getInstance().getSession(sessionId);
    }
}
