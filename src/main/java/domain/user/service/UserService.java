package domain.user.service;

import java.util.ArrayList;
import java.util.List;

import domain.user.db.UserRepository;
import domain.user.model.User;

public class UserService {
    private static final UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    private UserService() {
    }

    public void addUser(User user) {
        UserRepository.addUser(user);
    }

    public User findByUserId(String userId) {
        return UserRepository.findUserByUserId(userId);
    }

    public List<User> list() {
        return new ArrayList<>(UserRepository.findAll());
    }
}
