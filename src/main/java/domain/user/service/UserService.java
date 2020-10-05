package domain.user.service;

import domain.user.db.UserRepository;
import domain.user.model.User;

public class UserService {
    public static void addUser(User user) {
        UserRepository.addUser(user);
    }

    public static User findById(String userId) {
        return UserRepository.findUserById(userId);
    }
}
