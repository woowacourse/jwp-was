package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.QueryStringUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserCreateController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    void doGet(HttpRequest request, HttpResponse response) {
        try {
            DataBase.addUser(User.fromMap(QueryStringUtils.parse(request.getQueryString())));
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        try {
            DataBase.addUser(User.fromMap(QueryStringUtils.parse(request.getBody())));
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }
}
