package controller;

import db.DataBase;
import http.HttpResponse;
import http.request.HttpRequest;
import http.response.RedirectView;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.QueryStringUtils;

public class UserCreateController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        try {
            DataBase.addUser(User.fromMap(QueryStringUtils.parse(request.getBody())));
            response.write(new RedirectView("/index.html"));

        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }
}
