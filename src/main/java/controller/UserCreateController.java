package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.view.RedirectView;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.QueryStringUtils;

import java.util.Map;

public class UserCreateController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    void doPost(HttpRequest request, HttpResponse response) {

        Map<String, String> query = QueryStringUtils.parse(request.getBody());
        User user = new User(query.get("userId"), query.get("password"),
                query.get("name"), query.get("email"));

        DataBase.addUser(user);
        ResponseResolver.resolve(new RedirectView("/index.html"), response);
    }
}
