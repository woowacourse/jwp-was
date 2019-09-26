package web.controller.impl;

import web.controller.AbstractController;
import webserver.StaticFile;
import webserver.message.exception.NotFoundFileException;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String USER_LIST_PAGE = "/user/list.html";

    @Override
    protected Response doGet(final Request request) {
        String cookie = request.getHeaderValue("cookie");
        if (!cookie.contains("logined=true")) { // TODO 이거 어떻게 파싱 못하나
            return new Response.Builder().redirectUrl("/user/login").build();
        }

        try {
            return new Response.Builder().body(new StaticFile(TEMPLATES_PATH + USER_LIST_PAGE)).build();
        } catch (IOException | URISyntaxException e) {
            throw new NotFoundFileException();
        }
    }
}
