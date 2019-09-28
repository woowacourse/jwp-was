package controller;

import db.DataBase;
import http.common.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class UserListController extends AbstractController {
    private static class UserListControllerLazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }

    public static UserListController getInstance() {
        return UserListControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHttpHeader().getHeaderAttribute("Cookie").contains("logined=true")) {
            TemplateManager templateManager = TemplateManager.getInstance();
            Map<String, Object> users = Collections.singletonMap("users", DataBase.findAll());

            try {
                byte[] result = templateManager.render(new ModelAndView("user/list", users));
                httpResponse.addHeaderAttribute("Content-Type", ContentType.HTML + ";charset=utf-8");
                httpResponse.addHeaderAttribute("Content-Length", String.valueOf(result.length));
                httpResponse.setResponseStatus(ResponseStatus.OK);
                httpResponse.setBody(result);
            } catch (IOException e) {
                e.printStackTrace();
                httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            }

            return;
        }
        httpResponse.setResponseStatus(ResponseStatus.FOUND);
        httpResponse.addHeaderAttribute("Location", "/");
    }
}
