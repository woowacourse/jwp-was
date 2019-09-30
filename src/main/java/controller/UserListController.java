package controller;

import controller.core.AbstractController;
import controller.core.TemplateManager;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseStatus;
import webserver.http.session.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final String SESSION_USER_KEY = "user";
    private static final String USER_LIST_PATH = "user/list";
    private byte[] body;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doGet(httpRequest, httpResponse);
        if (body == null) {
            httpResponse.sendResponse(httpRequest);
            return;
        }
        httpResponse.sendResponse(body);
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        HttpSession session = httpRequest.getSession();
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            httpResponse.addStatus(ResponseStatus.of(200))
                    .addHeader(HttpHeaderField.CONTENT_TYPE, ResponseContentType.of(httpRequest.getRequestPath()));
            this.body = TemplateManager.getTemplatePage(USER_LIST_PATH).getBytes();
            return;
        }
        httpResponse.addStatus(ResponseStatus.of(302))
                .addHeader(HttpHeaderField.LOCATION, LOGIN_PAGE);
    }
}
