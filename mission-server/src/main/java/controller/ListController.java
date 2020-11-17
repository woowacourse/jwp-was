package controller;

import application.AbstractController;
import db.DataBase;
import dto.UserListResponse;
import exception.AttributeNotFoundException;
import exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import servlet.HttpRequest;
import servlet.HttpResponse;
import template.TemplateEngine;

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
