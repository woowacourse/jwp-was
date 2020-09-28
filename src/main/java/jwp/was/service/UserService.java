package jwp.was.service;

import java.util.Objects;
import jwp.was.configure.annotation.Autowired;
import jwp.was.configure.annotation.Service;
import jwp.was.controller.dto.UserRequest;
import jwp.was.db.DataBase;
import jwp.was.model.User;

@Service
public class UserService {

    @Autowired
    private DataBase dataBase;

    public User createUser(UserRequest userRequest) {
        User user = userRequest.toUser();
        if (Objects.nonNull(dataBase.findUserById(user.getUserId()))) {
            throw new IllegalArgumentException("userId가 중복되었습니다.");
        }
        dataBase.addUser(user);
        return user;
    }
}
