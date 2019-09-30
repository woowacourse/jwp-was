package model.user;

import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFactory {
    private static final Logger log = LoggerFactory.getLogger(UserFactory.class);

    public static User createUser(HttpRequest request) {
        try {
            String userId = request.getData("userId");
            String password = request.getData("password");
            String name = request.getData("name");
            String email = request.getData("email");
            return new User(userId, password, name, email);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new UserCreateException("입력 값을 확인해 주세요.");
        }
    }
}
