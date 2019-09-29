package controller;

import controller.core.AbstractController;
import controller.core.TemplateManager;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private byte[] body;

    @Override
    public void service(OutputStream out, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doGet(httpRequest, httpResponse);
        if (body == null) {
            httpResponse.sendResponse(out, httpRequest);
            return;
        }
        httpResponse.sendResponse(out, body);
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if(httpRequest.getLogin() != null && httpRequest.getLogin().equals("true")) {
            httpResponse.addStatus(ResponseStatus.of(200));
            httpResponse.addHeader(HttpHeaderField.CONTENT_TYPE, ResponseContentType.of(httpRequest.getRequestPath()));
            this.body = TemplateManager.getTemplatePage("user/list").getBytes();
            return;
        }
        httpResponse.addStatus(ResponseStatus.of(302));
        httpResponse.addHeader(HttpHeaderField.LOCATION, LOGIN_PAGE);
    }
}
