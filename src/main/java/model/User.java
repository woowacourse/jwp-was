package model;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User fromMap(Map<String, String> info) throws IllegalAccessException {
        User user = new User();
        for (Field field : User.class.getDeclaredFields()) {
            if (!info.containsKey(field.getName())) {
               throw new IllegalArgumentException("wrong user argument");
            }

            String value = info.get(field.getName());
            field.set(user, value);
        }

        return user;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
