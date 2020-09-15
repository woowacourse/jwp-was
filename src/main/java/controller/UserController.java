package controller;

import http.request.HttpRequest;
import http.request.RequestParams;
import http.response.HttpResponse;
import model.db.DataBase;
import model.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestParams params = httpRequest.getParams();
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        DataBase.addUser(user);
        logger.debug("save userId : {}", user.getUserId());

        httpResponse.sendRedirect("/index.html");
    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("userController doGet");
    }
}
