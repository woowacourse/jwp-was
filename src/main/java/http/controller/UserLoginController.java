package http.controller;

import db.DataBase;
import http.HttpResponse;
import http.exception.NotFoundUserException;
import http.exception.NotMatchPasswordException;
import http.request.HttpRequest;
import model.User;

import java.util.Optional;

public class UserLoginController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        //Todo 에러 페이지
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String requestUserId = request.getData("userId");
        String requestPassword = request.getData("password");
        Optional<User> mayBeUser = Optional.ofNullable(DataBase.findUserById(requestUserId));
        User user = mayBeUser.orElseThrow(() -> new NotFoundUserException("존재하지 않는 유저입니다."));
        if (!user.isEqualPassword(requestPassword)) {
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }
        response.setLoginSuccess("/");
    }
}
