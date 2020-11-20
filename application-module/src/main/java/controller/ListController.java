package controller;

import utils.HandlebarsHelper;
import db.DataBase;
import domain.controller.AbstractController;
import domain.request.HttpRequest;
import domain.response.HttpResponse;
import dto.UserListResponse;
import java.util.ArrayList;
import java.util.Optional;
import utils.StaticFileType;

public class ListController extends AbstractController {

    private static final String LOGINED = "logined";

    private ListController() {
        super();
    }

    public static ListController getInstance() {
        return Cache.LIST_CONTROLLER;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean isLogined = Optional.ofNullable(httpRequest.getSession().getAttribute(LOGINED))
            .map(value -> Boolean.parseBoolean(value.toString()))
            .orElse(false);

        if (!isLogined) {
            httpResponse.sendRedirect("/");
            return;
        }

        UserListResponse userListResponse = UserListResponse.of(new ArrayList<>(DataBase.findAll()));
        String content = HandlebarsHelper.apply("user/list", userListResponse);

        httpResponse.forward(content);
    }

    private static class Cache {

        private static final ListController LIST_CONTROLLER = new ListController();
    }
}
