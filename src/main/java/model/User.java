package model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static Map<String, User> users = new HashMap<>();

    private String userId;
    private String password;
    private String name;
    private String email;

    public static User save(User user) {
        int userCountBeforeCreatingNewUser = users.size();
        users.put(user.userId, user);
        if (userCountBeforeCreatingNewUser == users.size()) {
            throw new IllegalStateException("회원가입에 실패했습니다.");
        }
        return users.get(user.userId);
    }

    public static User findUser(String userId) {
        return users.get(userId);
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
