package webserver.controller;

import db.UserDataBase;
import webserver.TemplateFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserListController extends AbstractController {
    @Override
    protected HttpResponse get(HttpRequest httpRequest) {
        if ("true".equals(httpRequest.getCookieValue("logined"))) {
            return HttpResponse.ok()
                .body(TemplateFactory.of("user/list", UserDataBase.findAll()))
                .build();
        }

        return HttpResponse.ok()
            .bodyByPath("./templates/user/login.html")
            .build();
    }
}
