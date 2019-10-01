package http.controller;

import db.DataBase;
import http.common.HttpSession;
import http.exception.NotFoundUserException;
import http.exception.NotMatchPasswordException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;

import java.util.Optional;

public class UserLoginController extends AbstractController {
    public static final String URL = "/user/login";
    public static final String USER = "user";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        //Todo 에러 페이지
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String requestUserId = request.getData("userId");
        String requestPassword = request.getData("password");
        User user = checkLogin(response, requestUserId, requestPassword);
        HttpSession session = request.getSession();

        setLoginSuccessResponse(response, user, session);
    }

    private User checkLogin(HttpResponse response, String requestUserId, String requestPassword) {
        Optional<User> mayBeUser = Optional.ofNullable(DataBase.findUserById(requestUserId));
        User user = mayBeUser.orElseThrow(() -> {
            setLoginFailResponse(response);
            return new NotFoundUserException("존재하지 않는 유저입니다.");
        });
        if (!user.isEqualPassword(requestPassword)) {
            setLoginFailResponse(response);
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    private void setLoginSuccessResponse(HttpResponse response, User user, HttpSession session) {
        session.setAttribute(USER, user);

        response.sendRedirect("/index.html");
    }

    private void setLoginFailResponse(HttpResponse response) {
        response.sendRedirect("/user/login_failed.html");
    }
}
