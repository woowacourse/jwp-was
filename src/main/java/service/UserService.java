package service;

import db.DataBase;
import java.util.Objects;
import model.User;
import service.dto.UserRequest;

public class UserService {

    private final DataBase dataBase;

    public UserService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public User createUser(UserRequest userRequest) {
        User user = userRequest.toUser();
        if (Objects.nonNull(dataBase.findUserById(user.getUserId()))) {
            throw new IllegalArgumentException("userId가 중복되었습니다.");
        }
        dataBase.addUser(user);
        return user;
    }
}
