package http.supoort;

import http.model.HttpRequest;
import http.session.SessionManager;

import java.io.InputStream;

public class HttpRequestFactory {
    private SessionManager sessionManager;

    public HttpRequestFactory(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public HttpRequest getRequest(InputStream inputStream) {
        HttpRequest httpRequest = HttpRequestParser.parse(inputStream);

        httpRequest.bindSession(sessionManager.getSession());
        return httpRequest;
    }
}
