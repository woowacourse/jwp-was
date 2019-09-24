package controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class UserLoginController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        // TODO : request의 body를 보고 로그인한 유저의 정보 확인하기
        String userId = request.findRequestBodyParam("userId");
        String password = request.findRequestBodyParam("password");

        if (DataBase.hasUser(userId)) {
            User user = DataBase.findUserById(userId);
            if (user.matchPassword(password)) {
                // TODO : 로그인 성공

            }
        }
        // TODO : 로그인 실패


    }
}
