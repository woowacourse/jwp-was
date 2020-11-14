package application.service;

import application.controller.WrongUserIdPasswordException;
import application.db.DataBase;
import application.model.User;
import request.HttpRequest;

public class UserService {

    public void create(HttpRequest request) {
        User user = new User(
            request.getValueFromFormData("userId"),
            request.getValueFromFormData("password"),
            request.getValueFromFormData("name"),
            request.getValueFromFormData("email")
        );
        DataBase.addUser(user);
    }

    /**
     * 로그인 성공시 정상종료
     * 로그인 실패시 WrongUserIdPasswordException 예외를 던짐
     */
    public void login(HttpRequest request) throws WrongUserIdPasswordException {
        String userId = request.getValueFromFormData("userId");
        String password = request.getValueFromFormData("password");

        User user = DataBase.findUserById(userId);

        if (!user.getPassword().equals(password)) {
            throw new WrongUserIdPasswordException("userId is exist but password is wrong.");
        }
    }
}
