package interfaces.urlmapper;

import java.util.Arrays;

import interfaces.CreateUserController;
import interfaces.LoginController;
import interfaces.UserListController;
import web.controller.Controller;
import webserver.RequestMapping;

public enum UrlAppender {
    CREATE_USER_CONTROLLER("/user/create", new CreateUserController()),
    LOGIN_CONTROLLER("/user/login", new LoginController()),
    USER_LIST_CONTROLLER("/user/list", new UserListController());

    private final String url;
    private final Controller controller;

    UrlAppender(String url, Controller controller) {
        this.url = url;
        this.controller = controller;
    }

    static {
        Arrays.stream(values())
            .forEach(v -> RequestMapping.addUrl(v.url, v.controller));
    }
}
