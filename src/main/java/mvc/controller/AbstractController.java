package mvc.controller;

import server.http.response.HttpResponse;
import was.http.context.BasicSessionHandler;
import was.http.context.Session;
import was.http.context.SessionHandler;
import server.http.request.HttpRequest;
import mvc.view.View;
import was.http.servlet.AbstractServlet;

import java.util.UUID;

public class AbstractController extends AbstractServlet implements Controller {
    private static final SessionHandler SESSION_HANDLER = BasicSessionHandler.getInstance();

    @Override
    protected final HttpResponse doPost(HttpRequest request) {
        return post(request).createResponse();
    }

    @Override
    protected final HttpResponse doGet(HttpRequest request) {
        return get(request).createResponse();
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

    protected Session getSession(HttpRequest httpRequest) {
        return SESSION_HANDLER.getSession(UUID.fromString(httpRequest.getHeader("Cookie")));
    }
}
