package controller;

import controller.core.AbstractController;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseStatus;
import webserver.http.session.HttpSession;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {
    private static final String SET_COOKIE_FORMAT = "JSESSIONID=%s; path=/";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doGet(httpRequest, httpResponse);
        httpResponse.sendResponse(httpRequest);
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession session = httpRequest.getHttpSession();
        httpResponse.addStatus(ResponseStatus.OK)
                .addHeader(HttpHeaderField.CONTENT_TYPE, ResponseContentType.of(httpRequest.getRequestPath()))
                .addCookie(String.format(SET_COOKIE_FORMAT, session.getId()));
    }
}
