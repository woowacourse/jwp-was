package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.view.RedirectView;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.QueryStringUtils;

public class UserCreateController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    void doGet(HttpRequest request, HttpResponse response) {

    }

    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        try {
            DataBase.addUser(User.fromMap(QueryStringUtils.parse(request.getBody())));
            response.render(new RedirectView("/index.html"));

        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }
}
