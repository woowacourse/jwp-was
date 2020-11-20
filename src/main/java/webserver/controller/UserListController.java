package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserListController extends AbstractController {
    @Override
    protected HttpResponse get(HttpRequest httpRequest) {
        if ("true".equals(httpRequest.getCookieValue("logined"))) {
            return HttpResponse.ok()
                .body("./templates/user/list.html")
                .build();
        }

        return HttpResponse.ok()
            .body("./templates/user/login.html")
            .build();
    }
}
