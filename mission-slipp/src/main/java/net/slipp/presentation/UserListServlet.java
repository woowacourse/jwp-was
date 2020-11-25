package net.slipp.presentation;

import net.slipp.application.UserService;
import net.slipp.application.UserServiceFactory;
import net.slipp.presentation.dto.UsersResponse;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.model.servlet.HttpServlet;
import kr.wootecat.dongle.model.servlet.TemplateRenderer;

public class UserListServlet extends HttpServlet {

    private static final String USER_LIST_URL = "/user/list";
    private static final String REQUIRE_AUTH_PAGE_URL = "/user/login.html";

    private static final String COOKIE_NAME_WITH_LOGIN_CHECK = "logined";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        boolean isNotLogin = !request.hasCookie(COOKIE_NAME_WITH_LOGIN_CHECK, true);
        if (isNotLogin) {
            response.sendRedirect(REQUIRE_AUTH_PAGE_URL);
            return;
        }

        UserService userService = UserServiceFactory.getInstance();
        UsersResponse userResponse = userService.findAll();
        TemplateRenderer templateRenderer = TemplateRenderer.ofDefault();
        templateRenderer.forward(response, USER_LIST_URL, userResponse);
    }
}
