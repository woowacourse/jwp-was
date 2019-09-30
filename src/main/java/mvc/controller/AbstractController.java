package mvc.controller;

import mvc.view.View;
import server.http.request.HttpRequest;
import server.http.response.HttpResponse;
import was.http.servlet.AbstractServlet;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class AbstractController extends AbstractServlet implements Controller {
    @Override
    protected final HttpResponse doPost(HttpRequest request) {
        HttpResponse response = post(request).createResponse();
        postProcess(request, response);
        return response;
    }

    @Override
    protected final HttpResponse doGet(HttpRequest request) {
        HttpResponse response = get(request).createResponse();
        postProcess(request, response);
        return response;
    }

    @Override
    public View get(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public View post(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public View put(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public View delete(HttpRequest httpRequest) {
        return null;
    }

    private void postProcess(HttpRequest request, HttpResponse response) {
        addSessionIdInCookie(request, response);
    }

    private void addSessionIdInCookie(HttpRequest request, HttpResponse response) {
        UUID sessionId = request.getSessionId();
        if (sessionId != null) {
            response.setCookie("SESSIONID", sessionId.toString());
        }
    }
}
