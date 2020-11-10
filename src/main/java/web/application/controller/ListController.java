package web.application.controller;

import db.DataBase;
import java.util.ArrayList;
import java.util.Optional;
import web.HandlebarsHelper;
import web.application.dto.UserListResponse;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;
import web.server.utils.StaticFileType;

public class ListController extends AbstractController {

    private ListController() {
        super();
    }

    public static ListController getInstance() {
        return ListController.Cache.LIST_CONTROLLER;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean isLogined = Optional.ofNullable(httpRequest.getSession().getAttribute("logined"))
            .map(value -> Boolean.parseBoolean(value.toString()))
            .orElse(false);

        if (!isLogined) {
            httpResponse.forward("templates/index.html", StaticFileType.HTML);
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
