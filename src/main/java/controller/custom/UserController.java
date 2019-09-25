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
    private static final String MAPPING_URL = "/user";
    private static final String INDEX_PAGE = "/index.html";
    private static final String LOGIN_FAILED_PAGE = "/login_failed.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        // 회원가입
        if (request.getPath().contains("/create")) {
            Map<String, String> body = request.getBody();
            User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
            DataBase.addUser(user);
            response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
            return;
        }

        // 로그인
        if (request.getPath().contains("/login")) {
            User user = DataBase.findUserById(request.getBodyValueBy("userId"));

            if (isSignedUpUser(request, user)) {
                response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
                return;
            }
            response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + LOGIN_FAILED_PAGE, HttpStatus.REDIRECT);
            return;
        }
    }

    private boolean isSignedUpUser(HttpRequest request, User user) {
        return user != null && request.getBodyValueBy("password").equals(user.getPassword());
    }

    @Override
    public boolean isMapping(HttpRequest request) {
        return request.getPath().startsWith(MAPPING_URL);
    }
}
