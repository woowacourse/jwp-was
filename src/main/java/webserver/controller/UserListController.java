package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private UserListController() {
    }

    public static UserListController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String[] logined = httpRequest.getHeaderFieldValue("Cookie").split("=");

        logger.debug("logined : {}", logined);
        if(logined[1].equals("true")) {
            httpResponse.sendRedirect("/user/list.html",true);
            return;
        }

        httpResponse.sendRedirect("/user/login.html",false);
    }
}
