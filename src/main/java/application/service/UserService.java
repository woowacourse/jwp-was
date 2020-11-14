package application.service;

import application.controller.WrongUserIdPasswordException;
import application.db.DataBase;
import application.dto.LoginRequest;
import application.dto.UserCreateRequest;
import application.dto.UserResponse;
import application.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserService {

    public UserResponse create(UserCreateRequest request) {
        try {
            User user = new User(
                request.getUserId(),
                request.getPassword(),
                request.getName(),
                request.getEmail()
            );
            DataBase.addUser(user);

            return UserResponse.of(DataBase.findUserById(user.getUserId()));
        } catch (WrongUserIdPasswordException e) {
            throw new IllegalStateException();
        }
    }

    /**
     * 로그인 성공시 정상종료
     * 로그인 실패시 WrongUserIdPasswordException 예외를 던짐
     */
    public boolean isExistUser(LoginRequest request) {
        String userId = request.getUserId();
        String password = request.getPassword();

        try {
            User user = DataBase.findUserById(userId);

            if (!user.getPassword().equals(password)) {
                throw new WrongUserIdPasswordException("userId is exist but password is wrong.");
            }
            return true;
        } catch (WrongUserIdPasswordException e) {
            return false;
        }
    }

    public List<UserResponse> findAllUsers() {
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : DataBase.findAll()) {
            userResponses.add(UserResponse.of(user));
        }
        return Collections.unmodifiableList(userResponses);
    }
}
