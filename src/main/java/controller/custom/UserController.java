package controller.custom;

import controller.AbstractController;
import db.DataBase;
import model.User;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpStatus;

import java.util.Map;

public class UserController extends AbstractController {
    private static final String MAPPING_URL = "/user/create";
    private static final String INDEX_PAGE = "/index.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> body = request.getBody();
        User user = new User(body.get("userId"), body.get("password")
                , body.get("name"), body.get("email"));
        DataBase.addUser(user);
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
    }

    @Override
    public boolean isMapping(HttpRequest request) {
        return MAPPING_URL.equals(request.getPath());
    }
}
