package web.application.controller;

import java.util.ArrayList;
import java.util.Optional;

import db.DataBase;
import lombok.RequiredArgsConstructor;
import web.application.dto.UserListResponse;
import web.application.util.TemplateEngine;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;
import web.server.utils.StaticFileType;

@RequiredArgsConstructor
public class ListController extends AbstractController {

    private final TemplateEngine templateEngine;

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Optional<Object> logined = httpRequest.getSession()
            .getAttribute("logined");

        boolean isLogined = logined.map(Object::toString)
            .map(Boolean::parseBoolean)
            .orElse(Boolean.FALSE);

        if (!isLogined) {
            httpResponse.forward("templates/index.html", StaticFileType.HTML);
            return;
        }

        UserListResponse userListResponse = UserListResponse.of(new ArrayList<>(DataBase.findAll()));
        String content = templateEngine.apply("user/list", userListResponse);

        httpResponse.forward(content);
    }
}
