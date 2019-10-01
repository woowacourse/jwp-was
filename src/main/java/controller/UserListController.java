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
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final String USER_LIST_PATH = "user/list";
    private static final String LOGIN_PAGE_PATH = "/user/login.html";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        HttpSession session = httpRequest.getHttpSession();
        byte[] body = (session != null && session.getAttribute(SESSION_USER_KEY) != null) ?
                TemplateManager.getTemplatePage(USER_LIST_PATH).getBytes() : null;

        doGet(httpRequest, httpResponse, body);

        if (body == null) {
            httpResponse.sendResponse(httpRequest);
            return;
        }
        httpResponse.sendResponse(body);

    }

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse, byte[] body) {
        if (body == null) {
            httpResponse.addStatus(ResponseStatus.FOUND)
                    .addHeader(HttpHeaderField.LOCATION, LOGIN_PAGE_PATH);
            return;
        }
        httpResponse.addStatus(ResponseStatus.OK)
                .addHeader(HttpHeaderField.CONTENT_TYPE, ResponseContentType.of(httpRequest.getRequestPath()));
    }
}
