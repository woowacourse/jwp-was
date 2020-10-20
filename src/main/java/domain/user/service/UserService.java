package domain.user.service;

import domain.user.db.UserRepository;
import domain.user.model.User;

public class UserService {
    private static final UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    public void addUser(User user) {
        UserRepository.addUser(user);
    }

    private UserService() {
    }

    public User findByUserId(String userId) {
        return UserRepository.findUserByUserId(userId);
    }
}
