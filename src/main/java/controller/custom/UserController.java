package controller.custom;

import controller.AbstractController;
import db.DataBase;
import model.User;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UserController extends AbstractController {
    private static final String MAPPING_URL = "/user";
    private static final String INDEX_PAGE = "/index.html";
    private static final String LOGIN_FAILED_PAGE = "/user/login_failed.html";

    private void create(HttpRequest request, HttpResponse response) {
        Map<String, String> body = request.getBody();
        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        DataBase.addUser(user);
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
    }

    private void login(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getBodyValueBy("userId"));

        if (isSignedUpUser(request, user)) {
            response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
            return;
        }
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + LOGIN_FAILED_PAGE, HttpStatus.FORBIDDEN);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String methodName = request.getPath().substring("/user".length() + 1);

        Map<String, Method> methods = new HashMap<>();

        methods.put("/create", this.getClass().getDeclaredMethod("create", HttpRequest.class, HttpResponse.class));
        methods.put("/login", this.getClass().getDeclaredMethod("login", HttpRequest.class, HttpResponse.class));
        methods.get(methodName).setAccessible(true);

        methods.get(methodName).invoke(UserController.class, request, response);

//        // 회원가입
//        if (request.getPath().contains("/create")) {
//            Map<String, String> body = request.getBody();
//            User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
//            DataBase.addUser(user);
//            response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
//            return;
//        }
//
//        // 로그인
//        if (request.getPath().contains("/login")) {
//            User user = DataBase.findUserById(request.getBodyValueBy("userId"));
//
//            if (isSignedUpUser(request, user)) {
//                response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
//                return;
//            }
//            response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + LOGIN_FAILED_PAGE, HttpStatus.FORBIDDEN);
//        }
    }

    private boolean isSignedUpUser(HttpRequest request, User user) {
        return user != null && request.getBodyValueBy("password").equals(user.getPassword());
    }

    @Override
    public boolean isMapping(HttpRequest request) {
        return request.getPath().startsWith(MAPPING_URL);
    }
}
