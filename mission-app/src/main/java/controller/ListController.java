package controller;

import db.DataBase;
import lombok.RequiredArgsConstructor;
import web.application.dto.UserListResponse;
import web.application.exception.AuthorizationException;
import web.application.util.TemplateEngine;
import web.server.domain.exception.AttributeNotFoundException;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

@RequiredArgsConstructor
public class ListController extends AbstractController {

    private final TemplateEngine templateEngine;

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Object logined = httpRequest.getSession()
                .getAttribute("logined");

            boolean isLogined = Boolean.parseBoolean(logined.toString());

            if (!isLogined) {
                throw new AuthorizationException();
            }

            UserListResponse userListResponse = UserListResponse.of(DataBase.findAll());
            String content = templateEngine.apply("user/list", userListResponse);
            httpResponse.forward(content);
        } catch (AttributeNotFoundException | AuthorizationException e) {
            httpResponse.sendRedirect("/");
        }
    }
}
